package usedmarket.usedmarket.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.member.presentation.dto.request.*;
import usedmarket.usedmarket.domain.member.presentation.dto.response.*;
import usedmarket.usedmarket.global.jwt.JwtTokenProvider;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    @Value("${file.dir}")
    private String fileDir;

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

//    public MyInfoResponseDto findMyInfo() {
//        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
//                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));
//
//        return MyInfoResponseDto.builder()
//                .member(member)
//                .build();
//    }

    public CompleteProductsResponseDto manageCompleteBoards(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return CompleteProductsResponseDto.builder()
                .member(member)
                .build();
    }

    public ProductCommentsResponseDto manageComments(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return ProductCommentsResponseDto.builder()
                .member(member)
                .build();
    }

    @Transactional
    public void updateMyInfo(MemberUpdateRequestDto requestDto) throws IOException {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        String saveFilename = "";

        if(requestDto.getFile().isEmpty()) {
            //만약에 비어있다면 원래 이미지를 저장
            saveFilename = member.getImgPath();
        }
        else {
            String originFilename = requestDto.getFile().getOriginalFilename();

            saveFilename = UUID.randomUUID() + "_" + originFilename;

            File save = new File(fileDir + saveFilename);
            requestDto.getFile().transferTo(save);
        }

        member.update(saveFilename,
                fileDir + saveFilename,
                requestDto.getNickname()
        );
    }

    @Transactional
    public void updatePassword(MemberPasswordUpdateRequestDto requestDto) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        if(!passwordEncoder.matches(requestDto.getOriginPassword(), member.getPassword())) {
            throw new IllegalArgumentException("기존 패스워드가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder, requestDto.getNewPassword());
    }

    @Transactional
    public boolean withdraw(MemberWithdrawalRequestDto requestDto) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        File file = new File(member.getImgPath());
        file.delete();

        memberRepository.delete(member);
        if(!memberRepository.existsById(member.getId())) {
            return true;
        }
        else return false;
    }

    @Transactional
    public Long surveyMember(Long id, MemberSurveyRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        member.updateMannerTemperature(requestDto);
        return member.getId();
    }
}
