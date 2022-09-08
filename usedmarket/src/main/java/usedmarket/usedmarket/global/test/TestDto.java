package usedmarket.usedmarket.global.test;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class TestDto {

    private List<MultipartFile> file;
    private int price;
}
