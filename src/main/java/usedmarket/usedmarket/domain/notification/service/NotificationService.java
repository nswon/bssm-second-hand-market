package usedmarket.usedmarket.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.notification.domain.Notification;
import usedmarket.usedmarket.domain.notification.domain.NotificationRepository;
import usedmarket.usedmarket.domain.notification.presentation.dto.req.NotificationCreateRequestDto;
import usedmarket.usedmarket.global.security.jwt.SecurityUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveNotification(NotificationCreateRequestDto req) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요"));

        Notification notification = req.toEntity();

        notification.confirmUser(member);
        notificationRepository.save(notification);
    }

    public void sendNotification(String token, String nickname) throws ExecutionException, InterruptedException {
        Message message = Message.builder()
                .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(WebpushNotification.builder()
                                .setTitle("Mojaty")
                                .setBody(nickname + "님이 모티티를 등록하였습니다.")
                                .build())
                        .build())
                .setToken(token)
                .build();

        FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    public List<String> getNotificationTokenAll() {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요"));

        return notificationRepository.findAll().stream()
                .map(Notification::getToken)
                .collect(Collectors.toList());
    }

    public void deleteNotification() {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요"));

        Notification notification = notificationRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        notificationRepository.delete(notification);
    }
}
