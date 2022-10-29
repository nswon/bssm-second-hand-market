package usedmarket.usedmarket.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final NotificationService notificationService;

    public void sendMessages(String product) throws ExecutionException, InterruptedException {

    }
}
