package usedmarket.usedmarket.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import usedmarket.usedmarket.domain.chat.domain.ChatRoom;
import usedmarket.usedmarket.domain.chat.domain.ChatRoomRepository;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatRoomProductResponseDto;
import usedmarket.usedmarket.domain.chat.presentation.dto.response.ChatRoomResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.products.domain.Product;
import usedmarket.usedmarket.domain.products.domain.ProductsRepository;
import usedmarket.usedmarket.global.security.jwt.SecurityUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ProductsRepository productsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatRoomResponseDto createRoom(Long productId) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        Optional<ChatRoom> chatRoom = chatRoomRepository.findByProduct(product);

        if(chatRoom.isPresent()) {
            return ChatRoomResponseDto.builder()
                    .chatRoom(chatRoom.get())
                    .build();
        }

        ChatRoom createChatRoom = ChatRoom.builder()
                .product(product)
                .member(member)
                .build();

        chatRoomRepository.save(createChatRoom);

        return ChatRoomResponseDto.builder()
                .chatRoom(createChatRoom)
                .build();
    }

    public List<ChatRoomResponseDto> findRoomAll() {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        return chatRoomRepository.findByMember(member).stream()
                .map(ChatRoomResponseDto::new)
                .collect(Collectors.toList());
    }

    public ChatRoomResponseDto findRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .map(ChatRoomResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
    }

    public ChatRoomProductResponseDto getProductById(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        return ChatRoomProductResponseDto.builder()
                .chatRoom(chatRoom)
                .build();
    }
}
