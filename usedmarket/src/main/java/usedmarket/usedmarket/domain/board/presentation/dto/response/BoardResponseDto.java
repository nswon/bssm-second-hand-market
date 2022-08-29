package usedmarket.usedmarket.domain.board.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.board.domain.Board;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private String title;
    private String imgPath;
    private int price;
    private String content;
    private String writer;

    @Builder
    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.imgPath = board.getImgPath();
        this.price = board.getPrice();
        this.content = board.getContent();
        this.writer = board.getWriter().getNickname();
    }
}
