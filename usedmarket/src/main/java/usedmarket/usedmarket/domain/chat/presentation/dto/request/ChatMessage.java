package usedmarket.usedmarket.domain.chat.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    private Long roomId;
    private String sender;
    private String message;
}
