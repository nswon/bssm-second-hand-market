package usedmarket.usedmarket.domain.chat.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;
import usedmarket.usedmarket.domain.chat.presentation.dto.request.ChatMessage;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatRoomProductResponseDto;
import usedmarket.usedmarket.domain.chat.service.ChatContentService;
import usedmarket.usedmarket.domain.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatMessageApiController {

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final ChatContentService chatContentService;

    @MessageMapping("comm/message")
    public void message(ChatMessage message) {
        if(message.getType().equals(ChatMessage.MessageType.ENTER)) {
            ChatRoomProductResponseDto dto = chatService.getProductById(message.getRoomId());
            message.setMessage("상품 사진 : " + dto.getImgUrl() + "\n" +
                               "상품 가격 : " + dto.getPrice() + "\n" +
                               "상품 제목 : " + dto.getTitle());
        }
        chatContentService.saveChatContent(message);
        sendingOperations.convertAndSend("/sub/comm/room/" + message.getRoomId(), message);
    }
}
