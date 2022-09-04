package usedmarket.usedmarket.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.member.presentation.dto.request.*;
import usedmarket.usedmarket.domain.member.presentation.dto.response.*;
import usedmarket.usedmarket.domain.member.service.MemberService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public boolean join(@RequestBody @Valid MemberJoinRequestDto requestDto) {
        return memberService.join(requestDto);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid MemberLoginRequestDto requestDto) {
        return memberService.login(requestDto);
    }

    @GetMapping("/{id}/products")
    public MemberResponseDto findMember(@PathVariable("id") Long id) {
        return memberService.findMember(id);
    }

    @GetMapping("/{id}/complete")
    public CompleteProductsResponseDto manageCompleteBoards(@PathVariable("id") Long id) {
        return memberService.manageCompleteBoards(id);
    }

    @GetMapping("/{id}/comments")
    public ManageCommentsResponseDto manageComments(@PathVariable("id") Long id) {
        return memberService.manageComments(id);
    }

    @PutMapping("/edit")
    public void updateMyInfo(@RequestBody MemberUpdateRequestDto requestDto) throws IOException {
        memberService.updateMyInfo(requestDto);
    }

    @PutMapping("/edit/password")
    public void updatePassword(@RequestBody MemberPasswordUpdateRequestDto requestDto) {
        memberService.updatePassword(requestDto);
    }

    @PostMapping("/{id}/confirm")
    public Long surveyMember(@PathVariable("id") Long id, @RequestBody MemberSurveyRequestDto requestDto) {
        return memberService.surveyMember(id, requestDto);
    }
}
