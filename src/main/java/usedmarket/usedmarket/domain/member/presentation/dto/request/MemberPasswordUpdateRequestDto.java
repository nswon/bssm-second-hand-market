package usedmarket.usedmarket.domain.member.presentation.dto.request;

import lombok.Getter;

@Getter
public class MemberPasswordUpdateRequestDto {

    private String originPassword;
    private String newPassword;
}
