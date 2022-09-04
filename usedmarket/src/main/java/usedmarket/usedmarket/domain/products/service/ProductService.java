package usedmarket.usedmarket.domain.products.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.products.domain.ProductStatus;
import usedmarket.usedmarket.domain.products.presentation.dto.request.ProductStatusRequestDto;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductAllResponseDto;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.products.domain.Product;
import usedmarket.usedmarket.domain.products.domain.ProductsRepository;
import usedmarket.usedmarket.domain.products.presentation.dto.request.ProductRequestDto;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductDetailResponseDto;
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

    private final ProductsRepository productsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createBoard(ProductRequestDto requestDto) throws IOException {
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
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요.")));

        productsRepository.save(product);
        product.addSaleBoard();
        return product.getId();
    }

    public List<ProductAllResponseDto> findAllBoard() {
        return productsRepository.findAll().stream()
                .filter(board -> !board.getProductStatus().equals(ProductStatus.COMPLETE))
                .map(ProductAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public ProductDetailResponseDto detailBoard(Long id) {
        return productsRepository.findById(id)
                .filter(board -> !board.getProductStatus().equals(ProductStatus.COMPLETE))
                .map(ProductDetailResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));
    }

    @Transactional
    public void statusBoard(Long id, ProductStatusRequestDto requestDto) {
        Product product = productsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        product.updateStatus(requestDto.getStatus());
    }

    public List<ProductAllResponseDto> searchBoard(String keyword) {
        return productsRepository.findByTitleContaining(keyword).stream()
                .map(ProductAllResponseDto::new)
                .collect(Collectors.toList());
    }

    //TODO : 이미지 수정 안됨..
    @Transactional
    public Long updateBoard(Long id, ProductRequestDto requestDto) throws IOException {
        Product product = productsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        if(!product.getWriter().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 판매글은 수정할 수 없습니다.");
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
                        requestDto.getContent()
        );

        return product.getId();
    }

    @Transactional
    public Long deleteBoard(Long id) {
        Product product = productsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        if(!product.getWriter().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 판매글은 삭제할 수 없습니다.");
        }

        File file = new File(product.getImgPath());
        file.delete();

        productsRepository.delete(product);
        return product.getId();
    }
}
