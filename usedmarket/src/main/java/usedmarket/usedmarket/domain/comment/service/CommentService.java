package usedmarket.usedmarket.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.board.domain.BoardRepository;
import usedmarket.usedmarket.domain.comment.domain.Comment;
import usedmarket.usedmarket.domain.comment.domain.CommentRepository;
import usedmarket.usedmarket.domain.comment.presentation.dto.request.CommentRequestDto;
import usedmarket.usedmarket.domain.comment.presentation.dto.response.CommentResponseDto;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long createComment(Long id, CommentRequestDto requestDto) {
        Comment comment = requestDto.toEntity();

        comment.confirmWriter(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요.")));

        comment.confirmBoard(boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다.")));

        commentRepository.save(comment);
        return comment.getId();
    }

    public List<CommentResponseDto> findAllComment() {
        return commentRepository.findAll().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
