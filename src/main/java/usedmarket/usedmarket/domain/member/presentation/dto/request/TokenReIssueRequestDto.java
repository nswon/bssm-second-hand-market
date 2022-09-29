package usedmarket.usedmarket.domain.member.presentation.dto.request;

import lombok.Getter;

@Getter
public class TokenReIssueRequestDto {

    private String email;
    private String refreshToken;
}
