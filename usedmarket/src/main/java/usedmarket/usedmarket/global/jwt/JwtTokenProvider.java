package usedmarket.usedmarket.global.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import usedmarket.usedmarket.global.redis.RedisService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;

    private final RedisService redisService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60;
    private final long REFRESH_TOKEN_VALID_TIME = 30 * 24 * 60 * 60 * 1000L;
    private static final String HEADER_ACCESS_TOKEN = "ACCESS_TOKEN";

    public String createAccessToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader(HEADER_ACCESS_TOKEN);
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claimsJws.getBody().getExpiration().before(new Date());
    }

    public void checkRefreshToken(String email, String refreshToken) {
        String redisRefreshToken = redisService.getValues(email);
        if(!refreshToken.equals(redisRefreshToken)) {
            throw new IllegalArgumentException("Refresh 토큰이 일치하지 않습니다.");
        }

        if(validateToken(refreshToken)) {
            throw new IllegalArgumentException("Refresh 토큰이 만료되었습니다. 다시 로그인해주세요.");
        }
    }
}
