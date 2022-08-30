package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String nickname;
    private int countBoard; //게시글 개수
    //프로필이미지
    //매너온도
    //거래 후기 댓글

    @Builder
    public MemberResponseDto(Member member) {
        this.nickname = member.getNickname();
        this.countBoard = member.getBoardList().size();
    }
}
