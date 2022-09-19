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
import usedmarket.usedmarket.domain.productLike.domain.ProductLikeQuerydslRepository;
import usedmarket.usedmarket.domain.products.domain.ProductQuerydslRepository;
import usedmarket.usedmarket.global.file.FileService;
import usedmarket.usedmarket.global.jwt.JwtTokenProvider;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    private final FileService fileService;
    private final ProductQuerydslRepository productQuerydslRepository;
    private final ProductLikeQuerydslRepository productLikeQuerydslRepository;

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
        return true;
    }

    @Transactional
    public TokenResponseDto login(MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().name());
        Cookie cookie = new Cookie("ACCESS_TOKEN", accessToken);
        cookie.setHttpOnly(true);

        return TokenResponseDto.builder()
                .cookie(cookie)
                .build();
    }

    public MemberResponseDto findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public CompleteProductsResponseDto getCompleteProductsById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return CompleteProductsResponseDto.builder()
                .member(member)
                .build();
    }

    public ProductCommentsResponseDto getCommentsById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return ProductCommentsResponseDto.builder()
                .member(member)
                .build();
    }

    @Transactional
    public void updateMember(MemberUpdateRequestDto requestDto) throws IOException {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        if(!member.getImgPath().isEmpty()) {
            fileService.deleteFile(member.getImgPath());
        }
        member.updateImgPath(fileService.saveFile(requestDto.getFile()).getImgPath());
        member.updateGetImgUrl(fileService.saveFile(requestDto.getFile()).getImgUrl());

        member.updateMember(requestDto.getNickname());
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
    public boolean withdrawMember(MemberWithdrawalRequestDto requestDto) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        fileService.deleteFile(member.getImgPath());
        memberRepository.delete(member);
        return true;
    }

    @Transactional
    public Long surveyMember(Long memberId, MemberSurveyRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        member.updateMannerTemperature(requestDto);
        return member.getId();
    }

    public List<LikeProductResponseDto> getLikeProductsById(Long memberId, String order) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        return productLikeQuerydslRepository.getProductLikeByMember(member, order).stream()
                .map(LikeProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<MemberProductResponseDto> getProductsByOrder(String order) {
        return productQuerydslRepository.getProductsByOrder(order).stream()
                .map(MemberProductResponseDto::new)
                .collect(Collectors.toList());
    }
}
