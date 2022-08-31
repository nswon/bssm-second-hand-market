package usedmarket.usedmarket.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.member.presentation.dto.request.*;
import usedmarket.usedmarket.domain.member.presentation.dto.response.MemberResponseDto;
import usedmarket.usedmarket.domain.member.presentation.dto.response.MyInfoResponseDto;
import usedmarket.usedmarket.domain.member.presentation.dto.response.TokenResponseDto;
import usedmarket.usedmarket.global.jwt.JwtTokenProvider;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public boolean join(MemberJoinRequestDto requestDto) {
        if(memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 사용자입니다.");
        }

        if(emailService.verityCode(requestDto.getCheckCode())) {
            throw new IllegalArgumentException("이메일 인증 코드가 일치하지 않습니다.");
        }

        Member member = memberRepository.save(requestDto.toEntity());
        member.encodedPassword(passwordEncoder);
        member.addUserAuthority();

        if(memberRepository.existsById(member.getId())) {
           return true;
        }
        else return false;
    }

    @Transactional
    public TokenResponseDto login(MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().name());
        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public MemberResponseDto findMember(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public MyInfoResponseDto findMyInfo() {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        return new MyInfoResponseDto(member);
    }

    @Transactional
    public MemberResponseDto updateMyInfo(MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        member.update(requestDto.getNickname());
        return MemberResponseDto.builder()
                .member(member)
                .build();
    }

    @Transactional
    public MemberResponseDto updatePassword(MemberPasswordUpdateRequestDto requestDto) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        if(!passwordEncoder.matches(requestDto.getOriginPassword(), member.getPassword())) {
            throw new IllegalArgumentException("기존 패스워드가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder, requestDto.getNewPassword());
        return MemberResponseDto.builder()
                .member(member)
                .build();
    }

    @Transactional
    public Long withdrawal(MemberWithdrawalRequestDto requestDto) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(member);
        return member.getId();
    }
}
