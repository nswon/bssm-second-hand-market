package usedmarket.usedmarket.global.security.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import usedmarket.usedmarket.global.security.auth.CustomUserDetails;

public class SecurityUtil {
    public static String getLoginUserEmail() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
