package usedmarket.usedmarket.domain.products.presentation.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import usedmarket.usedmarket.domain.products.domain.Product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProductRequestDto {

    @NotNull(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 30)
    private String title;

    @NotNull(message = "이미지를 넣어주세요.")
    private MultipartFile file;

    @NotNull(message = "가격을 입력해주세요.")
    private int price;

    private String content;

    @NotNull(message = "카테고리를 선택해주세요.")
    private String category;

    public Product toEntity() {
        return Product.builder()
                .title(title)
                .price(price)
                .content(content)
                .build();
    }

}
