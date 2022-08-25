package usedmarket.usedmarket.domain.member.presentation.dto.request;

import lombok.Getter;
import usedmarket.usedmarket.domain.member.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class EmailDto {

    @NotNull(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .build();
    }
}
