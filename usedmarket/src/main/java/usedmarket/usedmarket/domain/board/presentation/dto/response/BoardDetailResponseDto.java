package usedmarket.usedmarket.domain.board.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.board.domain.Board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardDetailResponseDto {

    private String imgPath;
    private String nickname;
    private String title;
    private int price;
    private String content;
    private LocalDateTime createdDate;
    private String status;
    private int likeNumber;
    private int commentNumber;
    private List<BoardRelatedMemberDto> boardRelatedMemberDtos;

    //조회수 추가
    @Builder
    public BoardDetailResponseDto(Board board) {
        this.imgPath = board.getImgPath();
        this.nickname = board.getWriter().getNickname();
        this.title = board.getTitle();
        this.price = board.getPrice();
        this.content = board.getContent();
        this.createdDate = board.getCreatedDate();
        this.status = board.getBoardStatus().name();
        this.likeNumber = board.getBoardLikeList().size();
        this.commentNumber = board.getCommentList().size();
        this.boardRelatedMemberDtos = board.getWriter().getBoardList().stream()
                .filter(b -> !(b.getId().equals(board.getId())))
                .map(BoardRelatedMemberDto::new)
                .collect(Collectors.toList());
    }
}
