package usedmarket.usedmarket.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberWithdrawalRequestDto;
import usedmarket.usedmarket.domain.member.service.MemberService;

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
