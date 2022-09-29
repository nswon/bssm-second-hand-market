package usedmarket.usedmarket.domain.products.presentation.dto.response;

import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.Product;

@Getter
public class ProductMemberResponseDto {

    private Long id;
    private String imgUrl;
    private String title;

    public ProductMemberResponseDto(Product product) {
        this.id = product.getId();
        this.imgUrl = product.getImgUrl();
        this.title = product.getTitle();
    }
}
