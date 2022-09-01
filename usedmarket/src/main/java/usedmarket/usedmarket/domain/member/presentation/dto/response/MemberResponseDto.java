package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.comment.presentation.dto.response.CommentResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String imgPath;
    private String nickname;
    private int countBoard;
    private double mannerTemperature;
    //거래 후기 댓글

    @Builder
    public MemberResponseDto(Member member) {
        this.imgPath = member.getImgPath();
        this.nickname = member.getNickname();
        this.countBoard = member.getBoardList().size();
        this.mannerTemperature = member.getMannerTemperature();
    }
}
