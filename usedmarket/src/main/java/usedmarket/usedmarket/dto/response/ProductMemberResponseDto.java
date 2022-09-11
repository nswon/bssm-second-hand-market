package usedmarket.usedmarket.dto.response;

import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.Product;

@Getter
public class ProductMemberResponseDto {

    private String imgPath;
    private String title;

    public ProductMemberResponseDto(Product product) {
        this.imgPath = product.getImgPath();
        this.title = product.getTitle();
    }
}
