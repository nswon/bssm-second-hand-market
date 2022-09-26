package usedmarket.usedmarket.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.chat.domain.ChatRoom;
import usedmarket.usedmarket.domain.chat.domain.ChatRoomRepository;
import usedmarket.usedmarket.domain.chat.presentation.dto.request.ChatMessage;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatAllResponseDto;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatDetailResponseDto;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.products.domain.Product;
import usedmarket.usedmarket.domain.products.domain.ProductsRepository;
import usedmarket.usedmarket.global.security.jwt.SecurityUtil;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ProductsRepository productsRepository;
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatContentService chatContentService;

    @Transactional
    public ChatResponseDto createRoom(Long productId) {
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        Optional<ChatRoom> isChatRoom = chatRoomRepository.findByProduct(product);

        if(isChatRoom.isPresent()) {
            return ChatResponseDto.builder()
                    .chatRoom(isChatRoom.get())
                    .build();
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .product(product)
                .member(member)
                .build();

        chatRoomRepository.save(chatRoom);
        return ChatResponseDto.builder()
                .chatRoom(chatRoom)
                .build();
    }

    public List<ChatAllResponseDto> findChatRoomAll() {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        return chatRoomRepository.findByMember(member).stream()
                .map(ChatAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public ChatDetailResponseDto findChatByRoomId(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .map(ChatDetailResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("시용자가 존재하지 않습니다."));
    }

    public void sendMessage(ChatMessage message) {
        if(message.getType().equals(ChatMessage.MessageType.ENTER)) {
            ChatRoom chatRoom = chatRoomRepository.findById(message.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

            message.setMessage(chatRoom.getProduct().getImgUrl() + "\n" +
                               chatRoom.getProduct().getPrice() + "\n" +
                               chatRoom.getProduct().getTitle());
        }
        chatContentService.saveMessage(message);
        sendingOperations.convertAndSend("/sub/chatting/room" + message.getRoomId(), message);
    }
}
