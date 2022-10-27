package usedmarket.usedmarket.domain.notification.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usedmarket.usedmarket.domain.notification.presentation.dto.req.NotificationCreateRequestDto;
import usedmarket.usedmarket.domain.notification.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationApiController {

    private final NotificationService notificationService;

    @PostMapping("/new")
    public void saveNotification(@RequestBody NotificationCreateRequestDto req) {
        notificationService.saveNotification(req);
    }
}
