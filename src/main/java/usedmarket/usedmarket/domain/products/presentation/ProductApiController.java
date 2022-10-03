package usedmarket.usedmarket.domain.products.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.products.domain.Category;
import usedmarket.usedmarket.domain.products.presentation.dto.request.ProductStatusRequestDto;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductDetailResponseDto;
import usedmarket.usedmarket.domain.products.presentation.dto.request.ProductRequestDto;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductAllResponseDto;
import usedmarket.usedmarket.domain.products.service.ProductService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/new")
    public boolean createProduct(@Valid ProductRequestDto requestDto) throws IOException {
        return productService.createProduct(requestDto);
    }

    @GetMapping("")
    public List<ProductAllResponseDto> findProductAll(@RequestParam(value = "productId", defaultValue = "-1") Long productId) {
        return productService.findProductAll(productId);
    }

    @GetMapping("/{productId}")
    public ProductDetailResponseDto findProductById(@PathVariable("productId") Long productId) {
        return productService.findProductById(productId);
    }

    @GetMapping("/categories")
    public List<ProductAllResponseDto> findProductByCategory(@RequestParam Category category,
                                                             @RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                             @RequestParam(value = "order") String order) {
        return productService.findProductByCategory(category, pageNumber, order);
    }

    @PutMapping("/{productId}")
    public boolean updateProductStatus(@PathVariable("productId") Long productId,
                                    @RequestBody @Valid ProductStatusRequestDto requestDto) {
        return productService.updateProductStatus(productId, requestDto);
    }

    @GetMapping("/search/products")
    public List<ProductAllResponseDto> searchProducts(@RequestParam("keyword") String keyword,
                                              @RequestParam(value = "page", defaultValue = "0") int pageNumber) {
        return productService.searchProducts(keyword, pageNumber);
    }

    @PutMapping("/{productId}/edit")
    public boolean updateProduct(@PathVariable("productId") Long productId,
                       @Valid ProductRequestDto requestDto) throws IOException {
        return productService.updateProduct(productId, requestDto);
    }

    @DeleteMapping("/{productId}")
    public boolean deleteProduct(@PathVariable("productId") Long productId) {
        return productService.deleteProduct(productId);
    }
}
