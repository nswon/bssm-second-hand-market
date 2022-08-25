package usedmarket.usedmarket.global.jwt;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getLoginUserEmail() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
