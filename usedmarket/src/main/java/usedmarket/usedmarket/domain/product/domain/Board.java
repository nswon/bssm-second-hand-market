package usedmarket.usedmarket.domain.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.member.domain.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "BOARD")
public class Board {

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
        writer.addProduct(this);
    }
}
