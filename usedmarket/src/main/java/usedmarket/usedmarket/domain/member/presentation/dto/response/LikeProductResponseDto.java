package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Getter;
import usedmarket.usedmarket.domain.productLike.domain.ProductLike;

import java.time.LocalDateTime;

@Getter
public class LikeProductResponseDto {

    private String imgUrl;
    private String status;
    private String title;
    private int price;
    private LocalDateTime createdDate;

    public LikeProductResponseDto(ProductLike productLike) {
        imgUrl = productLike.getProduct().getImgUrl();
        status = productLike.getProduct().getProductStatus().name();
        title = productLike.getProduct().getTitle();
        price = productLike.getProduct().getPrice();
        createdDate = productLike.getProduct().getCreatedDate();
    }
}
