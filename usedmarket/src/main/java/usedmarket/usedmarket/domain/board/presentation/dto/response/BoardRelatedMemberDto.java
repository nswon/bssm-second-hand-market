package usedmarket.usedmarket.domain.board.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.board.domain.Board;

@Getter
public class BoardRelatedMemberDto {

    private String imgPath;
    private String title;
    private int price;

    @Builder
    public BoardRelatedMemberDto(Board board) {
        this.imgPath = board.getImgPath();
        this.title = board.getTitle();
        this.price = board.getPrice();
    }
}
