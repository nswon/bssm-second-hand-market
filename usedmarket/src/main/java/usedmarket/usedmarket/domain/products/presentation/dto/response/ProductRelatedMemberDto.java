package usedmarket.usedmarket.domain.products.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.Product;

@Getter
public class ProductRelatedMemberDto {

    private String imgPath;
    private int price;

    @Builder
    public ProductRelatedMemberDto(Product product) {
        this.imgPath = product.getImgPath();
        this.price = product.getPrice();
    }
}
