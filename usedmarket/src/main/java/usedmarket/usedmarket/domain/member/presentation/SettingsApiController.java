package usedmarket.usedmarket.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberWithdrawalRequestDto;
import usedmarket.usedmarket.domain.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsApiController {

    private final MemberService memberService;

    @DeleteMapping("/withdraw")
    public boolean withdrawMember(@RequestBody MemberWithdrawalRequestDto requestDto) {
        return memberService.withdrawMember(requestDto);
    }
}
