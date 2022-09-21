package usedmarket.usedmarket.domain.chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.products.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByProduct(Product product);
    List<ChatRoom> findByMember(Member member);
}
