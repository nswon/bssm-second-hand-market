package usedmarket.usedmarket.domain.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import usedmarket.usedmarket.domain.member.domain.Member;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    boolean existsByKeywordAndMember(String keyword, Member member);
}
