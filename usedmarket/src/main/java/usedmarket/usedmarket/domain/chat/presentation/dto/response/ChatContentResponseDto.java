package usedmarket.usedmarket.domain.chat.presentation.dto.response;

import lombok.Getter;
import usedmarket.usedmarket.domain.chat.domain.ChatRoomContent;

import java.time.LocalDateTime;

@Getter
public class ChatContentResponseDto {

    private String content;
    private LocalDateTime sendTime;

    public ChatContentResponseDto(ChatRoomContent chatRoomContent) {
        this.content = chatRoomContent.getContent();
        this.sendTime = chatRoomContent.getSendTime();
    }
}
