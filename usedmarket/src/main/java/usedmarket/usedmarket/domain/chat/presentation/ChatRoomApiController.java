package usedmarket.usedmarket.domain.chat.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.chat.presentation.dto.request.ChatRoomRequestDto;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatRoomResponseDto;
import usedmarket.usedmarket.domain.chat.service.ChatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatRoomApiController {

    private final ChatService chatService;

    @PostMapping("/new")
    public ChatRoomResponseDto createRoom(@RequestBody ChatRoomRequestDto requestDto) {
        return chatService.createRoom((long) requestDto.getProductId());
    }

    @GetMapping("")
    public List<ChatRoomResponseDto> findRoomAll() {
        return chatService.findRoomAll();
    }

    @GetMapping("/{roomId}")
    public ChatRoomResponseDto findRoomById(@PathVariable("roomId") Long roomId) {
        return chatService.findRoomById(roomId);
    }
}
