package usedmarket.usedmarket.domain.chat.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.chat.domain.ChatRoom;

@Getter
public class ChatRoomProductResponseDto {

    private String imgUrl;
    private int price;
    private String title;

    @Builder
    public ChatRoomProductResponseDto(ChatRoom chatRoom) {
        this.imgUrl = chatRoom.getProduct().getImgUrl();
        this.price = chatRoom.getProduct().getPrice();
        this.title = chatRoom.getProduct().getTitle();
    }
}
