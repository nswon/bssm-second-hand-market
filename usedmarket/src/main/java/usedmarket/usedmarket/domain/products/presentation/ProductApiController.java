package usedmarket.usedmarket.domain.products.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.dto.request.ProductRequestDto;
import usedmarket.usedmarket.dto.request.ProductStatusRequestDto;
import usedmarket.usedmarket.dto.response.ProductAllResponseDto;
import usedmarket.usedmarket.dto.response.ProductDetailResponseDto;
import usedmarket.usedmarket.domain.products.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/new")
    public Long createProduct(ProductRequestDto requestDto) throws IOException {
        return productService.createProduct(requestDto);
    }

    @GetMapping("")
    public List<ProductAllResponseDto> findProductAll(@RequestParam(value = "page", defaultValue= "0") int pageNumber) {
        return productService.findProductAll(pageNumber);
    }

    @GetMapping("/{productId}")
    public ProductDetailResponseDto findProductById(@PathVariable("productId") Long productId) {
        return productService.findProductById(productId);
    }

    @PutMapping("/{productId}")
    public void updateProductStatus(@PathVariable("productId") Long productId,
                                    @RequestBody ProductStatusRequestDto requestDto) {
        productService.updateProductStatus(productId, requestDto);
    }

    @GetMapping("/search/products")
    public List<ProductAllResponseDto> searchProducts(@RequestParam("keyword") String keyword,
                                              @RequestParam(value = "page", defaultValue = "0") int pageNumber) {
        return productService.searchProducts(keyword, pageNumber);
    }

    @PutMapping("/{productId}/edit")
    public Long updateProduct(@PathVariable("productId") Long productId,
                       ProductRequestDto requestDto) throws IOException {
        return productService.updateProduct(productId, requestDto);
    }

    @DeleteMapping("/{productId}")
    public Long deleteProduct(@PathVariable("productId") Long productId) {
        return productService.deleteProduct(productId);
    }
}
