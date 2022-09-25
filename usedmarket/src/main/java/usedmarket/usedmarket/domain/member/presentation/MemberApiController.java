package usedmarket.usedmarket.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.member.presentation.dto.request.*;
import usedmarket.usedmarket.domain.member.presentation.dto.response.*;
import usedmarket.usedmarket.domain.member.service.MemberService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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

    @GetMapping("/{memberId}/products")
    public MemberResponseDto findMemberById(@PathVariable("memberId") Long memberId) {
        return memberService.findMemberById(memberId);
    }

    @GetMapping("/{memberId}/complete")
    public CompleteProductsResponseDto getCompleteProductsById(@PathVariable("memberId") Long memberId) {
        return memberService.getCompleteProductsById(memberId);
    }

    @GetMapping("/{memberId}/likes")
    public List<LikeProductResponseDto> getLikeProductsById(@PathVariable("memberId") Long memberId,
                                                            @RequestParam(value = "order")String order) {
        return memberService.getLikeProductsById(memberId, order);
    }

    @GetMapping("/{memberId}/comments")
    public ProductCommentsResponseDto getCommentsById(@PathVariable("memberId") Long memberId) {
        return memberService.getCommentsById(memberId);
    }

    @PutMapping("/edit")
    public boolean updateMember(MemberUpdateRequestDto requestDto) throws IOException {
        return memberService.updateMember(requestDto);
    }

    @PutMapping("/edit/password")
    public boolean updatePassword(@RequestBody MemberPasswordUpdateRequestDto requestDto) {
        return memberService.updatePassword(requestDto);
    }

    @PostMapping("/{memberId}/confirm")
    public boolean surveyMember(@PathVariable("memberId") Long memberId,
                             @RequestBody MemberSurveyRequestDto requestDto) {
        return memberService.surveyMember(memberId, requestDto);
    }
}
