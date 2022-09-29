package usedmarket.usedmarket.domain.notification.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

    private String imgUrl;
    private String keyword;
    private String title;
    private LocalDateTime createdDate;

    @Builder
    public NotificationResponseDto(String imgUrl, String keyword, String title, LocalDateTime createdDate) {
        this.imgUrl = imgUrl;
        this.keyword = keyword;
        this.title = title;
        this.createdDate = createdDate;
    }
}
