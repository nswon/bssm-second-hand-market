package usedmarket.usedmarket.domain.notification.presentation.dto.req;

import lombok.Getter;
import usedmarket.usedmarket.domain.notification.domain.Notification;

@Getter
public class NotificationCreateRequestDto {

    private String token;

    public Notification toEntity() {
        return Notification.builder()
                .token(token)
                .build();
    }
}
