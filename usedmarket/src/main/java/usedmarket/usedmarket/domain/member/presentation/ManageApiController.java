package usedmarket.usedmarket.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberPasswordUpdateRequestDto;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberUpdateRequestDto;
import usedmarket.usedmarket.domain.member.presentation.dto.response.*;
import usedmarket.usedmarket.domain.member.service.MemberService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manage")
public class ManageApiController {

    private final MemberService memberService;

    @GetMapping("/boards")
    public MyInfoResponseDto findMyInfo() {
        return memberService.findMyInfo();
    }

    @GetMapping("/complete")
    public ManageCompleteResponseDto manageCompleteBoards() {
        return memberService.manageCompleteBoards();
    }

    @GetMapping("/comments")
    public ManageCommentsResponseDto manageComments() {
        return memberService.manageComments();
    }

    @PutMapping("/edit")
    public MemberResponseDto updateMyInfo(@RequestBody MemberUpdateRequestDto requestDto) throws IOException {
        return memberService.updateMyInfo(requestDto);
    }

    @PutMapping("/edit/password")
    public MemberResponseDto updatePassword(@RequestBody MemberPasswordUpdateRequestDto requestDto) {
        return memberService.updatePassword(requestDto);
    }
}
