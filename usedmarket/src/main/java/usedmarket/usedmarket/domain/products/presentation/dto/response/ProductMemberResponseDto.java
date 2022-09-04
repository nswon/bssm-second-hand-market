package usedmarket.usedmarket.domain.products.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.Product;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class ProductMemberResponseDto {

    private final String imgPath;
    private final String nickname;
    private final int productNumber;
    private final double mannerTemperature;
    private final List<ProductRelatedMemberDto> relateProducts;

    //상점 후기 댓글들 조회 (해당 판매자 상품 로직이랑 똑같이 하면 됨)

    @Builder
    public ProductMemberResponseDto(Product product) {
        this.imgPath = product.getWriter().getImgPath();
        this.nickname = product.getWriter().getNickname();
        this.productNumber = product.getWriter().getProductList().size();
        this.mannerTemperature = product.getWriter().getMannerTemperature();
        this.relateProducts = product.getWriter().getProductList().stream()
                .filter(Objects::nonNull)
                .limit(2)
                .map(ProductRelatedMemberDto::new)
                .collect(Collectors.toList());
    }
}
