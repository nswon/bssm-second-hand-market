package usedmarket.usedmarket.domain.product.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.product.presentation.dto.request.ProductRequestDto;
import usedmarket.usedmarket.domain.product.presentation.dto.response.ProductResponseDto;
import usedmarket.usedmarket.domain.product.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/create")
    public Long createProduct(ProductRequestDto requestDto) throws IOException {
        return productService.createProduct(requestDto);
    }

    @GetMapping("/findAll")
    public List<ProductResponseDto> findAllProduct() {
        return productService.findAllProduct();
    }

    @GetMapping("/find/{id}")
    public ProductResponseDto detailProduct(@PathVariable("id") Long id) {
        return productService.detailProduct(id);
    }

    @GetMapping("/search")
    public List<ProductResponseDto> searchProduct(@RequestParam("keyword") String keyword) {
        return productService.searchProduct(keyword);
    }

    @PutMapping("/update/{id}")
    public Long updateProduct(@PathVariable("id") Long id, ProductRequestDto requestDto) throws IOException {
        return productService.updateProduct(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
