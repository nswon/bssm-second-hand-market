package usedmarket.usedmarket.domain.board.presentation.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import usedmarket.usedmarket.domain.board.domain.BoardStatus;

import java.util.List;

@Data
public class BoardRequestDto {

    private String title;

    private MultipartFile file;

    private int price;

    private String content;

}
