package usedmarket.usedmarket.domain.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByMember(Member member);
}
