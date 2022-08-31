package usedmarket.usedmarket.domain.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.BoardLike.domain.BoardLike;
import usedmarket.usedmarket.domain.comment.domain.Comment;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BOARD")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String imgName;

    private String imgPath;

    private int price;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikeList = new ArrayList<>();

    @Builder
    public Board(String title, String imgName, String imgPath, int price, String content) {
        this.title = title;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.price = price;
        this.content = content;
    }

    public void update(String title, String imgName, String imgPath, int price, String content) {
        this.title = title;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.price = price;
        this.content = content;
    }

    public void confirmWriter(Member writer) {
        this.writer = writer;
        writer.addBoard(this);
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    public void addBoardLike(BoardLike boardLike) {
        boardLikeList.add(boardLike);
    }
}
