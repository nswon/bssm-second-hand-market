package usedmarket.usedmarket.domain.chat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.products.domain.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomContent> contents = new ArrayList<>();

    @Builder
    public ChatRoom(Product product, Member member) {
        this.product = product;
        this.member = member;
    }

    public void addChatRoomContent(ChatRoomContent chatRoomContent) {
        contents.add(chatRoomContent);
    }
}
