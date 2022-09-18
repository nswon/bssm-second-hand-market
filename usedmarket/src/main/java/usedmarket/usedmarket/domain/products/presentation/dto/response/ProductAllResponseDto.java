package usedmarket.usedmarket.domain.products.presentation.dto.response;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
        this.imgPath = product.getGetImgUrl();
        this.status = product.getProductStatus().name();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.createdDate = product.getCreatedDate();
        this.likeNumber = product.getProductLikeList().size();
    }
}
