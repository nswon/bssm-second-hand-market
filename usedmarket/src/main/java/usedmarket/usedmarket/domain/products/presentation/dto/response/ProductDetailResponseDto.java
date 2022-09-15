package usedmarket.usedmarket.domain.products.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.products.domain.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ProductDetailResponseDto {

    private String imgPath;
    private String title;
    private int price;
    private int likeNumber;
    private int view;
    private String status;
    private LocalDateTime createdDate;
    private String nickname;
    private String content;
    private List<ProductMemberResponseDto> relateProduct;

    @Builder
    public ProductDetailResponseDto(Product product) {
        this.imgPath = product.getImgPath();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.likeNumber = product.getProductLikeList().size();
        this.view = product.getView();
        this.status = product.getProductStatus().name();
        this.createdDate = product.getCreatedDate();
        this.nickname = product.getWriter().getNickname();
        this.content = product.getContent();
        this.relateProduct = product.getWriter().getProductList().stream()
                .takeWhile(Objects::nonNull)
                .limit(6)
                .map(ProductMemberResponseDto::new)
                .collect(Collectors.toList());
    }
}
