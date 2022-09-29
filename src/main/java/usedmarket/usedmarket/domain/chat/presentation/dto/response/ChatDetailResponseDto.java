package usedmarket.usedmarket.domain.chat.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.chat.domain.ChatRoom;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChatDetailResponseDto {

    private String imgUrl;
    private String nickname;
    private List<ChatContentResponseDto> contents;

    @Builder
    public ChatDetailResponseDto(ChatRoom chatRoom) {
        this.imgUrl = chatRoom.getProduct().getWriter().getImgUrl();
        this.nickname = chatRoom.getProduct().getWriter().getNickname();
        this.contents = chatRoom.getContents().stream()
                .map(ChatContentResponseDto::new)
                .collect(Collectors.toList());
    }
}
