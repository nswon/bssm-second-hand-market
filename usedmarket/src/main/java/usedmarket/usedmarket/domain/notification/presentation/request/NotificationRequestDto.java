package usedmarket.usedmarket.domain.notification.presentation.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.notification.domain.Notification;

@Getter
@NoArgsConstructor
public class NotificationRequestDto {

    private String keyword;

    public Notification toEntity() {
        return Notification.builder()
                .keyword(keyword)
                .build();
    }
}
