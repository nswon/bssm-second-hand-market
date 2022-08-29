package usedmarket.usedmarket.domain.product.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.product.domain.Product;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private String title;
    private String imgPath;
    private int price;
    private String content;

    @Builder
    public ProductResponseDto(Product product) {
        this.title = product.getTitle();
        this.imgPath = product.getImgPath();
        this.price = product.getPrice();
        this.content = product.getContent();
    }
}
