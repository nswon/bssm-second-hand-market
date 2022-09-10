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
    public void like(@PathVariable("productId") Long productId) {
        productLikeService.like(productId);
    }

}
