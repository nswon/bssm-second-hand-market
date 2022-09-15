package usedmarket.usedmarket.domain.products.presentation.dto.request;

import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.ProductStatus;

@Getter
public class ProductStatusRequestDto {

    private ProductStatus status;

}
