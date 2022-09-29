package usedmarket.usedmarket.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.products.domain.ProductsRepository;
import usedmarket.usedmarket.domain.comment.domain.Comment;
import usedmarket.usedmarket.domain.comment.domain.CommentRepository;
import usedmarket.usedmarket.domain.comment.presentation.dto.request.CommentRequestDto;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.global.security.jwt.SecurityUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProductsRepository productsRepository;

    @Transactional
    public boolean createComment(Long productId, CommentRequestDto requestDto) {
        Comment comment = requestDto.toEntity();

        comment.confirmWriter(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요.")));

        comment.confirmProduct(productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다.")));

        commentRepository.save(comment);
        return true;
    }
}
