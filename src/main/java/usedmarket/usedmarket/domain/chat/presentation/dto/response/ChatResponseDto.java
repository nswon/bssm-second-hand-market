package usedmarket.usedmarket.domain.chat.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.chat.domain.ChatRoom;

@Getter
public class ChatResponseDto {

    private Long id;
    private String imgUrl;
    private String nickname;

    @Builder
    public  ChatResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.imgUrl = chatRoom.getProduct().getImgUrl();
        this.nickname = chatRoom.getMember().getNickname();
    }
}
