package usedmarket.usedmarket.domain.productLike.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.productLike.service.ProductLikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class ProductLikeApiController {

    private final ProductLikeService productLikeService;

    @PutMapping("/{productId}")
    public boolean like(@PathVariable("productId") Long productId) {
        return productLikeService.like(productId);
    }

}
