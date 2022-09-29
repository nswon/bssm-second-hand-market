package usedmarket.usedmarket.domain.chat.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.chat.domain.ChatRoom;

@Getter
public class ChatAllResponseDto {

    private String mImgUrl;
    private String nickname;
    private String pImgUrl;

    @Builder
    public ChatAllResponseDto(ChatRoom chatRoom) {
        this.mImgUrl = chatRoom.getProduct().getWriter().getImgUrl();
        this.nickname = chatRoom.getProduct().getWriter().getNickname();
        this.pImgUrl = chatRoom.getProduct().getImgUrl();
    }
}
