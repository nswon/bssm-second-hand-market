package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.ProductStatus;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductAllResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CompleteProductsResponseDto {

    private List<ProductAllResponseDto> completeProducts;

    @Builder
    public CompleteProductsResponseDto(Member member) {
        this.completeProducts = member.getProductList().stream()
                .filter(board -> board.getProductStatus().equals(ProductStatus.COMPLETE))
                .map(ProductAllResponseDto::new)
                .collect(Collectors.toList());
    }
}
