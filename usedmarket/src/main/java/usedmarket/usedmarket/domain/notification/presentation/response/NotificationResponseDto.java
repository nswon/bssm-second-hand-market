package usedmarket.usedmarket.domain.notification.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

    private String imgPath;
    private String keyword;
    private String title;
    private LocalDateTime createdDate;

    @Builder
    public NotificationResponseDto(String imgPath, String keyword, String title, LocalDateTime createdDate) {
        this.imgPath = imgPath;
        this.keyword = keyword;
        this.title = title;
        this.createdDate = createdDate;
    }
}
