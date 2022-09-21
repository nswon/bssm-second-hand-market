package usedmarket.usedmarket.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.chat.domain.ChatRoomContent;
import usedmarket.usedmarket.domain.chat.domain.ChatRoomContentRepository;
import usedmarket.usedmarket.domain.chat.domain.ChatRoomRepository;
import usedmarket.usedmarket.domain.chat.presentation.dto.request.ChatMessage;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatContentService {

    private final ChatRoomContentRepository chatRoomContentRepository;
    private final ChatRoomRepository chatRoomRepository;
    @Transactional
    public void saveChatContent(ChatMessage message) {
        ChatRoomContent content = ChatRoomContent.builder()
                .content(message.getMessage())
                .build();

        content.confirmChatRoom(chatRoomRepository.findById(message.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다.")));

        chatRoomContentRepository.save(content);
    }
}
