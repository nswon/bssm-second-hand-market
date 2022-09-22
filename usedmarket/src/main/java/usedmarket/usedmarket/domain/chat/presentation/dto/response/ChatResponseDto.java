package usedmarket.usedmarket.domain.chat.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.chat.domain.ChatRoom;

@Getter
public class ChatResponseDto {

    private String imgUrl;
    private String nickname;

    @Builder
    public  ChatResponseDto(ChatRoom chatRoom) {
        this.imgUrl = chatRoom.getProduct().getImgUrl();
        this.nickname = chatRoom.getMember().getNickname();
    }
}
