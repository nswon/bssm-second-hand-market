package usedmarket.usedmarket.domain.BoardLike.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import usedmarket.usedmarket.domain.board.domain.Board;
import usedmarket.usedmarket.domain.member.domain.Member;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    boolean existsByBoardAndMember(Board board, Member member);
    void deleteByBoardAndMember(Board board, Member member);
}
