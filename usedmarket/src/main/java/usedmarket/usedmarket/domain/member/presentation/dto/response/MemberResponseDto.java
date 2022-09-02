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
@NoArgsConstructor
public class MemberResponseDto {

    private String imgPath;
    private String nickname;
    private int countBoard;
    private double mannerTemperature;
    private List<BoardAllResponseDto> boardAllResponseDtos;
    //거래 후기 댓글

    @Builder
    public MemberResponseDto(Member member) {
        this.imgPath = member.getImgPath();
        this.nickname = member.getNickname();
        this.countBoard = member.getBoardList().size();
        this.mannerTemperature = member.getMannerTemperature();
        this.boardAllResponseDtos = member.getBoardList().stream().map(BoardAllResponseDto::new).collect(Collectors.toList());
    }
}
