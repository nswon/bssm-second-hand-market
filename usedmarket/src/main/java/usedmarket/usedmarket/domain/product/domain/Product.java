package usedmarket.usedmarket.domain.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String imgName;

    private String imgPath;

    private int price;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public Product(String title, String imgName, String imgPath, int price, String content) {
        this.title = title;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.price = price;
        this.content = content;
    }
}
