package usedmarket.usedmarket.domain.board.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.board.domain.Board;

import java.time.LocalDateTime;

@Getter
public class BoardAllResponseDto {

    private String imgPath;
    private String title;
    private LocalDateTime createdDate;
    private int price;

    @Builder
    public BoardAllResponseDto(Board board) {
        this.imgPath = board.getImgPath();
        this.title = board.getTitle();
        this.createdDate = board.getCreatedDate();
        this.price = board.getPrice();
    }
}
