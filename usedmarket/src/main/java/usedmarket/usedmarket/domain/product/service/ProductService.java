package usedmarket.usedmarket.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.product.domain.Product;
import usedmarket.usedmarket.domain.product.domain.ProductRepository;
import usedmarket.usedmarket.domain.product.presentation.dto.request.ProductRequestDto;
import usedmarket.usedmarket.domain.product.presentation.dto.response.ProductResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    @Value("${file.dir}")
    private String fileDir;

    private final ProductRepository productRepository;

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

}
