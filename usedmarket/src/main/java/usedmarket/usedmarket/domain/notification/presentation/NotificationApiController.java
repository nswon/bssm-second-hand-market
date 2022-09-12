package usedmarket.usedmarket.domain.notification.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.notification.presentation.request.NotificationRequestDto;
import usedmarket.usedmarket.domain.notification.presentation.response.NotificationResponseDto;
import usedmarket.usedmarket.domain.notification.service.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationApiController {

    private final NotificationService notificationService;

    @PostMapping("/new")
    public void createKeyword(@RequestBody NotificationRequestDto requestDto) {
        notificationService.createKeyword(requestDto);
    }

    @GetMapping("")
    public List<NotificationResponseDto> getProductsByKeywordAndCreatedDate() {
        return notificationService.getProductsByKeywordAndCreatedDate();
    }

    @PutMapping("/{notificationId}/edit")
    public void updateKeyword(@PathVariable("notificationId") Long notificationId,
                              @RequestBody NotificationRequestDto responseDto) {
        notificationService.updateKeyword(notificationId, responseDto);
    }

    @DeleteMapping("/{notificationId}")
    public void deleteKeyword(@PathVariable("notificationId") Long notificationId) {
        notificationService.deleteKeyword(notificationId);
    }
}
