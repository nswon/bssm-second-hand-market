package usedmarket.usedmarket.domain.products.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.Product;

import java.time.LocalDateTime;

@Getter
public class ProductAllResponseDto {

    private String imgPath;
    private String title;
    private int price;
    private LocalDateTime createdDate;
    private String status;
    private int likeNumber;

    //최근 본 상품 (3개 까지)

    @Builder
    public ProductAllResponseDto(Product product) {
        this.imgPath = product.getImgPath();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.createdDate = product.getCreatedDate();
        this.status = product.getProductStatus().name();
        this.likeNumber = product.getProductLikeList().size();
    }
}
