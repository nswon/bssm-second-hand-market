package usedmarket.usedmarket.domain.member.presentation.dto.request;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MemberUpdateRequestDto {

    private MultipartFile file;
    private String nickname;

}
