package usedmarket.usedmarket.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberJoinRequestDto;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberLoginRequestDto;
import usedmarket.usedmarket.domain.member.presentation.dto.response.MemberResponseDto;
import usedmarket.usedmarket.domain.member.presentation.dto.response.TokenResponseDto;
import usedmarket.usedmarket.domain.member.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public MemberResponseDto join(@RequestBody @Valid MemberJoinRequestDto requestDto) {
        return memberService.join(requestDto);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid MemberLoginRequestDto requestDto) {
        return memberService.login(requestDto);
    }
}
