package usedmarket.usedmarket.domain.notification.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.notification.presentation.request.NotificationRequestDto;
import usedmarket.usedmarket.domain.notification.presentation.response.NotificationResponseDto;
import usedmarket.usedmarket.domain.notification.service.NotificationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationApiController {

    private final NotificationService notificationService;

    @PostMapping("/new")
    public boolean createKeyword(@RequestBody @Valid NotificationRequestDto requestDto) {
        return notificationService.createKeyword(requestDto);
    }

    @GetMapping("")
    public List<NotificationResponseDto> getProductsByKeywordAndCreatedDate() {
        return notificationService.getProductsByKeywordAndCreatedDate();
    }

    @PutMapping("/{notificationId}/edit")
    public boolean updateKeyword(@PathVariable("notificationId") Long notificationId,
                              @RequestBody @Valid NotificationRequestDto responseDto) {
        return notificationService.updateKeyword(notificationId, responseDto);
    }

    @DeleteMapping("/{notificationId}")
    public boolean deleteKeyword(@PathVariable("notificationId") Long notificationId) {
        return notificationService.deleteKeyword(notificationId);
    }

    @DeleteMapping("/deleteAll")
    public boolean deleteKeywordAll() {
        return notificationService.deleteKeywordAll();
    }
}
