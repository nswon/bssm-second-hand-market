package usedmarket.usedmarket.domain.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    boolean existsByKeyword(String keyword);
}
