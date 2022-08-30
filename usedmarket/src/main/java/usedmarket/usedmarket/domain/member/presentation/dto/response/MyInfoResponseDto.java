package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.board.presentation.dto.response.BoardAllResponseDto;
import usedmarket.usedmarket.domain.comment.presentation.dto.response.CommentResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyInfoResponseDto {

    //이미지
    private String nickname;
    //매너온도
    //판매내역
    //구매내역
    //찜한 게시글
    //키워드 알림
    private List<BoardAllResponseDto> boardAllResponseDtos;
    private List<CommentResponseDto> commentResponseDtos;

    public MyInfoResponseDto(Member member) {
        this.nickname = member.getNickname();
        this.boardAllResponseDtos = member.getBoardList().stream().map(BoardAllResponseDto::new).collect(Collectors.toList());
        this.commentResponseDtos = member.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
