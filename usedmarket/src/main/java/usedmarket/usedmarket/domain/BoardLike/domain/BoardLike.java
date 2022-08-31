package usedmarket.usedmarket.domain.BoardLike.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.board.domain.Board;
import usedmarket.usedmarket.domain.member.domain.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BOARD_LIKE")
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public BoardLike(Board board, Member member) {
        this.board = board;
        this.member = member;
    }

    public void confirmBoard(Board board) {
        this.board = board;
        board.addBoardLike(this);
    }

    public void confirmMember(Member member) {
        this.member = member;
        member.addBoardLike(this);
    }

}
