package usedmarket.usedmarket.domain.BoardLike.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import usedmarket.usedmarket.domain.board.domain.Board;
import usedmarket.usedmarket.domain.member.domain.Member;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    boolean existsByBoardAndMember(Board board, Member member); //좋아요를 이미 눌렀다면 true
    void deleteByBoardAndMember(Board board, Member member); //좋아요 삭제
}
