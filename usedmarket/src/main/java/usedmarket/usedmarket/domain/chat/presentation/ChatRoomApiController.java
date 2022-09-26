package usedmarket.usedmarket.domain.chat.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.chat.domain.ChatRoom;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatAllResponseDto;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatDetailResponseDto;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatResponseDto;
import usedmarket.usedmarket.domain.chat.service.ChatService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatting")
public class ChatRoomApiController {

    private final ChatService chatService;

    @GetMapping("/room")
    public ChatResponseDto createChatRoom(@RequestParam("productId") Long productId) {
        return chatService.createRoom(productId);
    }

    @GetMapping("")
    public List<ChatAllResponseDto> findAllRoom() {
        return chatService.findChatRoomAll();
    }

    @GetMapping("/room/{roomId}")
    public ChatDetailResponseDto findChatByRoomId(@PathVariable("roomId") Long roomId) {
        return chatService.findChatByRoomId(roomId);
    }
}
