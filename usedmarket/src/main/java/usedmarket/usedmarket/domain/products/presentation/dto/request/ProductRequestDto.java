package usedmarket.usedmarket.domain.products.presentation.dto.request;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class ProductRequestDto {

    private String title;

    private MultipartFile file;

    private int price;

    private String content;

}
