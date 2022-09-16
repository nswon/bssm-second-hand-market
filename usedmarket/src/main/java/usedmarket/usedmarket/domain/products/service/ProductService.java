package usedmarket.usedmarket.domain.products.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.category.domain.Category;
import usedmarket.usedmarket.domain.category.service.CategoryService;
import usedmarket.usedmarket.domain.products.domain.ProductQuerydslRepository;
import usedmarket.usedmarket.domain.products.domain.ProductStatus;
import usedmarket.usedmarket.domain.products.presentation.dto.request.ProductStatusRequestDto;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductAllResponseDto;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.products.domain.Product;
import usedmarket.usedmarket.domain.products.domain.ProductsRepository;
import usedmarket.usedmarket.domain.products.presentation.dto.request.ProductRequestDto;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductDetailResponseDto;
import usedmarket.usedmarket.global.file.FileService;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    private final ProductsRepository productsRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final CategoryService categoryService;
    private final ProductQuerydslRepository productQuerydslRepository;

    @Transactional
    public boolean createProduct(ProductRequestDto requestDto) throws IOException {
        Product product = requestDto.toEntity();

        product.confirmWriter(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요.")));

        product.updateImgPath(fileService.saveFile(requestDto.getFile()));

        Category category = categoryService.getCategoryByName(requestDto.getCategory());
        product.confirmCategory(category);

        productsRepository.save(product);
        product.addSaleStatus();
        return true;
    }

    public List<ProductAllResponseDto> findProductAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        return productsRepository.findAll(pageable).stream()
                .filter(product -> !product.getProductStatus().equals(ProductStatus.COMPLETE))
                .map(ProductAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductAllResponseDto> findProductByCategory(String categoryName, int pageNumber, String order) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        return productQuerydslRepository.getProductByCategoryAndOrder(
                categoryService.getCategoryByName(categoryName), order, pageable).stream()
                .map(ProductAllResponseDto::new)
                .collect(Collectors.toList()
                );
    }

    public ProductDetailResponseDto findProductById(Long productId) {
        return productsRepository.findById(productId)
                .filter(board -> !board.getProductStatus().equals(ProductStatus.COMPLETE))
                .map((product) -> {
                    product.updateView();
                    return ProductDetailResponseDto.builder().product(product).build();
                })
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

        if(!product.getImgPath().isEmpty()) {
            fileService.deleteFile(product.getImgPath());
        }

        Category category = categoryService.getCategoryByName(requestDto.getCategory());

        product.updateImgPath(fileService.saveFile(requestDto.getFile()));
        product.updateProduct(requestDto.getTitle(), requestDto.getPrice(), category, requestDto.getContent());

        return product.getId();
    }

    @Transactional
    public Long deleteProduct(Long productId) {
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        if(!product.getWriter().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 판매글은 삭제할 수 없습니다.");
        }

        if(!product.getImgPath().isEmpty()) {
            fileService.deleteFile(product.getImgPath());
        }
        productsRepository.delete(product);

        return product.getId();
    }
}
