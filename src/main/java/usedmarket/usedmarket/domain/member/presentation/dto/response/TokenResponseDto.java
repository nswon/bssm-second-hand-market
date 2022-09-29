package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;

@Getter
@NoArgsConstructor
public class TokenResponseDto {
    private Cookie cookie;

    @Builder
    public TokenResponseDto(Cookie cookie) {
        this.cookie = cookie;
    }
}
