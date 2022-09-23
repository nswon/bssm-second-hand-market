package usedmarket.usedmarket.domain.chat.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import usedmarket.usedmarket.domain.chat.presentation.dto.request.ChatMessage;
import usedmarket.usedmarket.domain.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
public class MessageApiController {

    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {
        chatService.sendMessage(message);
    }
}
