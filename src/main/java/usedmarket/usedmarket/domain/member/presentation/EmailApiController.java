package usedmarket.usedmarket.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usedmarket.usedmarket.domain.member.presentation.dto.request.EmailDto;
import usedmarket.usedmarket.domain.member.service.EmailService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailApiController {

    private final EmailService emailService;

    @PostMapping("/join")
    public String sendSimpleMessage(@RequestBody @Valid EmailDto request) throws Exception {
        emailService.sendSimpleMessage(request.getEmail());
        return "성공";
    }
}
