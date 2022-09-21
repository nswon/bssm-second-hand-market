package usedmarket.usedmarket.domain.chat.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.chat.domain.ChatRoom;

@Getter
public class ChatRoomResponseDto {

    private Long roomId;
    private String imgPath;
    private String nickname;

    @Builder
    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.roomId = chatRoom.getId();
        this.imgPath = chatRoom.getMember().getImgPath();
        this.nickname = chatRoom.getMember().getNickname();
    }
}
