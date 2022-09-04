package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductAllResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyInfoResponseDto {

    private String imgPath;
    private String nickname;
    private double mannerTemperature;
    private LocalDateTime createdDate;
    private int completeProductNumber;
    private List<ProductAllResponseDto> products;

    @Builder
    public MyInfoResponseDto(Member member) {
        this.imgPath = member.getImgPath();
        this.nickname = member.getNickname();
        this.mannerTemperature = member.getMannerTemperature();
        this.createdDate = member.getCreatedDate();
        this.completeProductNumber = CompleteProductsResponseDto.builder()
                .member(member)
                .build()
                .getBoardCompleteResponseDtos().size();
        this.products = member.getProductList().stream()
                    .map(ProductAllResponseDto::new)
                    .collect(Collectors.toList());
        }
}
