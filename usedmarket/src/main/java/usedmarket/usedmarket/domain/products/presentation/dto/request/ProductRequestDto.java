package usedmarket.usedmarket.domain.products.presentation.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequestDto {

    private String title;

    private MultipartFile file;

    private int price;

    private String content;

}
