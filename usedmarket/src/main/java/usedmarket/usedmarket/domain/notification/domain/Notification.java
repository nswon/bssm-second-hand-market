package usedmarket.usedmarket.domain.notification.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.global.entity.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NOTIFICATION")
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void confirmMember(Member member) {
        this.member = member;
        member.addNotification(this);
    }

    public void updateKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Builder
    public Notification(String keyword) {
        this.keyword = keyword;
    }
}
