package usedmarket.usedmarket.domain.member.domain;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import usedmarket.usedmarket.domain.productLike.domain.ProductLike;
import usedmarket.usedmarket.domain.products.domain.Product;
import usedmarket.usedmarket.domain.comment.domain.Comment;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberSurveyRequestDto;
import usedmarket.usedmarket.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String imgPath;

    private String imgUrl;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private double mannerTemperature = 36.5;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ProductLike> productLikeList = new ArrayList<>();

    @Builder
    public Member(String email, String imgPath, String nickname, String password) {
        this.email = email;
        this.imgPath = imgPath;
        this.nickname = nickname;
        this.password = password;
    }

    public void encodedPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void addUserAuthority() {
        this.role = Role.ROLE_USER;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    public void addProductLike(ProductLike productLike) {
        productLikeList.add(productLike);
    }

    public void updateMember(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateFile(String imgPath, String imgUrl) {
        this.imgPath = imgPath;
        this.imgUrl = imgUrl;
    }

    public void updateMannerTemperature(MemberSurveyRequestDto requestDto) {
        switch (requestDto.getSurveyNumber()) {
            case 5 :
                this.mannerTemperature += 5;
                break;
            case 4 :
                this.mannerTemperature += 3;
                break;
            case 3 :
                this.mannerTemperature += 0;
                break;
            case 2 :
                this.mannerTemperature -= 3;
                break;
            case 1 :
                this.mannerTemperature -= 5;
                break;
            default:
                throw new IllegalArgumentException("설문 양식에 맞지 않습니다.");
        }
    }

    public List<Comment> getCommentList(List<Product> productList) {
        List<Comment> resultCommentsList = new ArrayList<>();
        for (Product product : productList) {
            resultCommentsList.addAll(product.getCommentList());
        }
        return resultCommentsList;
    }
}
