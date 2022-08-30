package usedmarket.usedmarket.domain.board.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.board.domain.Board;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardDetailResponseDto {

    private String imgPath;
    private String writer;
    private String title;
    private int price;
    private String content;
    private LocalDateTime createdDate;

    //조회수 추가
    //좋아요 수 추가
    //해당 사용자가 올린 다른 게시글들 조회

    @Builder
    public BoardDetailResponseDto(Board board) {
        this.imgPath = board.getImgPath();
        this.writer = board.getWriter().getNickname();
        this.title = board.getTitle();
        this.price = board.getPrice();
        this.content = board.getContent();
        this.createdDate = board.getCreatedDate();
    }
}
