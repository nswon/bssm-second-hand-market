package usedmarket.usedmarket.domain.notification.presentation.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.notification.domain.Notification;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class NotificationRequestDto {

//    @NotNull(message = "키워드를 입력해주세요.")
    private String keyword;

    public Notification toEntity() {
        return Notification.builder()
                .keyword(keyword)
                .build();
    }
}
