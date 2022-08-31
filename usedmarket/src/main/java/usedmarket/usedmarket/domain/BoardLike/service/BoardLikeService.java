package usedmarket.usedmarket.domain.BoardLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.BoardLike.domain.BoardLike;
import usedmarket.usedmarket.domain.BoardLike.domain.BoardLikeRepository;
import usedmarket.usedmarket.domain.board.domain.Board;
import usedmarket.usedmarket.domain.board.domain.BoardRepository;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void like(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        if(boardLikeRepository.existsByBoardAndMember(board, member)) {
            boardLikeRepository.deleteByBoardAndMember(board, member);
        }
        else {
            BoardLike boardLike = BoardLike.builder()
                    .board(board)
                    .member(member)
                    .build();

            boardLike.confirmBoard(board);
            boardLike.confirmMember(member);

            boardLikeRepository.save(boardLike);
        }
    }
}
