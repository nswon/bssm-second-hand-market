package usedmarket.usedmarket.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.notification.domain.Notification;
import usedmarket.usedmarket.domain.notification.domain.NotificationRepository;
import usedmarket.usedmarket.domain.notification.presentation.request.NotificationRequestDto;
import usedmarket.usedmarket.domain.notification.presentation.response.NotificationResponseDto;
import usedmarket.usedmarket.domain.products.domain.ProductQuerydslRepository;
import usedmarket.usedmarket.global.security.jwt.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final ProductQuerydslRepository productQuerydslRepository;

    @Transactional
    public void createKeyword(NotificationRequestDto requestDto) {
        Notification notification = requestDto.toEntity();

        notification.confirmMember(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요")));

        if(notificationRepository.existsByKeyword(requestDto.getKeyword())) {
            throw new IllegalArgumentException("이미 등록된 키워드입니다.");
        }

        notificationRepository.save(notification);
    }

    public List<NotificationResponseDto> getProductsByKeywordAndCreatedDate() {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        List<NotificationResponseDto> response = new ArrayList<>();
        for(int i=0; i<member.getNotificationList().size(); i++) {
            response.addAll(productQuerydslRepository.getProductsByKeywordAndCreatedDate(member.getNotificationList().get(i).getKeyword()));
        }

        return response;
    }

    @Transactional
    public void updateKeyword(Long notificationId, NotificationRequestDto requestDto) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("키워드가 존재하지 않습니다."));

        if(!notification.getMember().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 키워드는 수정할 수 없습니다.");
        }

        notification.updateKeyword(requestDto.getKeyword());
    }

    @Transactional
    public void deleteKeyword(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("키워드가 존재하지 않습니다."));

        if(!notification.getMember().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 키워드는 삭제할 수 없습니다.");
        }

        notificationRepository.delete(notification);
    }

    @Transactional
    public void deleteKeywordAll() {
        notificationRepository.deleteAllInBatch();
    }
}
