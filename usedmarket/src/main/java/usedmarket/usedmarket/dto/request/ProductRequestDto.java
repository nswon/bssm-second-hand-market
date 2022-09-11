package usedmarket.usedmarket.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import usedmarket.usedmarket.domain.products.domain.Product;

@Data
public class ProductRequestDto {

    private String title;

    private MultipartFile file;

    private int price;

    private String content;

    public Product toEntity() {
        return Product.builder()
                .title(title)
                .price(price)
                .content(content)
                .build();
    }

}
