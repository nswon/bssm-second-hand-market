package usedmarket.usedmarket.domain.productLike.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.Product;

import java.time.LocalDateTime;

@Getter
public class ProductAllResponseDto {

    private String imgPath;
    private String status;
    private String title;
    private int price;
    private LocalDateTime createdDate;
    private int likeNumber;

    @Builder
    public ProductAllResponseDto(Product product) {
        this.imgPath = product.getImgPath();
        this.status = product.getProductStatus().name();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.createdDate = product.getCreatedDate();
        this.likeNumber = product.getProductLikeList().size();
    }
}
