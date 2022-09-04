package usedmarket.usedmarket.domain.products.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.products.domain.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ProductDetailResponseDto {

    private String imgPath;
    private String title;
    private int price;
    private int likeNumber;

    //조회수

    private String status;
    private LocalDateTime createdDate;

    //카테고리별 상품들 조회 (6개씩 페이징 : 상품 이미지, 제목)

    private String content;
    private ProductMemberResponseDto member;

    //조회수 추가
    @Builder
    public ProductDetailResponseDto(Product product) {
        this.imgPath = product.getImgPath();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.likeNumber = product.getProductLikeList().size();
        this.status = product.getProductStatus().name();
        this.createdDate = product.getCreatedDate();
        this.content = product.getContent();
        this.member = ProductMemberResponseDto.builder().product(product).build();
    }
}
