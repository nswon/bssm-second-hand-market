package usedmarket.usedmarket.domain.products.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요.")));

        productsRepository.save(product);
        product.addSaleStatus();
        return product.getId();
    }

    public List<ProductAllResponseDto> findProductAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        return productsRepository.findAll(pageable).stream()
                .filter(board -> !board.getProductStatus().equals(ProductStatus.COMPLETE))
                .map(ProductAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public ProductDetailResponseDto findProductById(Long productId) {
        return productsRepository.findById(productId)
                .filter(board -> !board.getProductStatus().equals(ProductStatus.COMPLETE))
                .map(ProductDetailResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));
    }

    @Transactional
    public void updateProductStatus(Long productId, ProductStatusRequestDto requestDto) {
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        product.updateStatus(requestDto.getStatus());
    }

    public List<ProductAllResponseDto> searchProducts(String keyword, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        return productsRepository.findByTitleContaining(keyword, pageable).stream()
                .filter(board -> !board.getProductStatus().equals(ProductStatus.COMPLETE))
                .map(ProductAllResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateProduct(Long productId, ProductRequestDto requestDto) throws IOException {
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        if(!product.getWriter().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 판매글은 수정할 수 없습니다.");
        }

        String saveFilename = "";

        if(requestDto.getFile().isEmpty()) {
            saveFilename = product.getImgPath();
        }
        else {
            String originFilename = requestDto.getFile().getOriginalFilename();

            saveFilename = UUID.randomUUID() + "_" + originFilename;

            File save = new File(fileDir + saveFilename);
            requestDto.getFile().transferTo(save);
        }

        product.updateProduct(requestDto.getTitle(),
                       saveFilename,
                fileDir + saveFilename,
                        requestDto.getPrice(),
                        requestDto.getContent()
        );

        return product.getId();
    }

    @Transactional
    public Long deleteProduct(Long productId) {
        Product product = productsRepository.findById(productId)
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
