package usedmarket.usedmarket.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberJoinRequestDto;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberLoginRequestDto;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberPasswordUpdateRequestDto;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberUpdateRequestDto;
import usedmarket.usedmarket.domain.member.presentation.dto.response.MemberResponseDto;
import usedmarket.usedmarket.domain.member.presentation.dto.response.MyInfoResponseDto;
import usedmarket.usedmarket.domain.member.presentation.dto.response.TokenResponseDto;
import usedmarket.usedmarket.domain.member.service.MemberService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public Long join(@RequestBody @Valid MemberJoinRequestDto requestDto) {
        return memberService.join(requestDto);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid MemberLoginRequestDto requestDto) {
        return memberService.login(requestDto);
    }

    @GetMapping("/find/{id}")
    public MemberResponseDto findMember(@PathVariable("id") Long id) {
        return memberService.findMember(id);
    }

    @GetMapping("/myInfo")
    public MyInfoResponseDto findMyInfo() {
        return memberService.findMyInfo();
    }

    @PutMapping("/myInfo/update")
    public MemberResponseDto updateMyInfo(@RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.updateMyInfo(requestDto);
    }

    @PutMapping("/myInfo/password")
    public MemberResponseDto updatePassword(@RequestBody MemberPasswordUpdateRequestDto requestDto) {
        return memberService.updatePassword(requestDto);
    }
}
