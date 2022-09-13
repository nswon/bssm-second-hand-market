package usedmarket.usedmarket.domain.productLike.presentation.dto.request;

import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.ProductStatus;

@Getter
public class ProductStatusRequestDto {

    private ProductStatus status;

}
