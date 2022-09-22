package usedmarket.usedmarket.domain.member.presentation.dto.request;

import lombok.Getter;
import usedmarket.usedmarket.domain.member.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class MemberLoginRequestDto {

    @NotNull(message = "이메일을 입력해주세요.")
    @Email
    private String email;

//    @NotNull(message = "비밀번호를 입력해주세요.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{6,20}",
//            message = "영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 6자 ~ 20자여야 합니다.")
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
