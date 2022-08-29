package usedmarket.usedmarket.domain.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.product.domain.Product;
import usedmarket.usedmarket.domain.product.domain.ProductRepository;
import usedmarket.usedmarket.domain.product.presentation.dto.request.ProductRequestDto;
import usedmarket.usedmarket.domain.product.presentation.dto.response.ProductResponseDto;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    @Value("${file.dir}")
    private String fileDir;

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createProduct(ProductRequestDto requestDto) throws IOException {
        if(requestDto.getFile().isEmpty()) {
            throw new IllegalArgumentException("이미지가 들어오지 않았습니다.");
        }

        String originFilename = requestDto.getFile().getOriginalFilename();

        String saveFilename = UUID.randomUUID() + "_" + originFilename;

        File save = new File(fileDir + saveFilename);
        requestDto.getFile().transferTo(save);

        Product product = Product.builder()
                .title(requestDto.getTitle())
                .imgName(saveFilename)
                .imgPath(fileDir + saveFilename)
                .price(requestDto.getPrice())
                .content(requestDto.getContent())
                .build();

        product.confirmWriter(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용가능합니다.")));

        productRepository.save(product);
        return product.getId();
    }

    public List<ProductResponseDto> findAllProduct() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public ProductResponseDto detailProduct(Long id) {
        return productRepository.findById(id)
                .map(ProductResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("프러덕트가 존재하지 않습니다."));
    }

    public List<ProductResponseDto> searchProduct(String keyword) {
        return productRepository.findByTitleContaining(keyword).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateProduct(Long id, ProductRequestDto requestDto) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프러덕트가 존재하지 않습니다."));

        if(!product.getWriter().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 게시글은 수정할 수 없습니다.");
        }

        String saveFilename = "";

        if(requestDto.getFile().isEmpty()) {
            //만약에 비어있다면 원래 이미지를 저장
            saveFilename = product.getImgPath();
        }
        else {
            String originFilename = requestDto.getFile().getOriginalFilename();

            saveFilename = UUID.randomUUID() + "_" + originFilename;

            File save = new File(fileDir + saveFilename);
            requestDto.getFile().transferTo(save);
        }

        product.update(requestDto.getTitle(),
                       saveFilename,
                fileDir + saveFilename,
                        requestDto.getPrice(),
                        requestDto.getContent());

        return product.getId();
    }

    @Transactional
    public Long deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프러덕트가 존재하지 않습니다."));

        if(!product.getWriter().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 게시글은 삭제할 수 없습니다.");
        }

        File file = new File(product.getImgPath());
        log.info("외부에서 가져온 파일 이름 = " + file);
        file.delete();

        productRepository.delete(product);
        return product.getId();
    }
}
