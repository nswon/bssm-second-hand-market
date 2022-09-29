package usedmarket.usedmarket.domain.products.presentation.dto.request;

import lombok.Getter;
import usedmarket.usedmarket.domain.products.domain.ProductStatus;

import javax.validation.constraints.NotNull;

@Getter
public class ProductStatusRequestDto {

    @NotNull(message = "상태를 선택해주세요.")
    private ProductStatus status;

}
