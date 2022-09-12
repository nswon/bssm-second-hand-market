package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.Product;

import java.time.LocalDateTime;

@Getter
public class MemberProductResponseDto {

    private String imgPath;
    private String status;
    private String title;
    private int price;
    private LocalDateTime createdDate;

    public MemberProductResponseDto(Product product) {
        this.imgPath = product.getImgPath();
        this.status = product.getProductStatus().name();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.createdDate = product.getCreatedDate();
    }

}
