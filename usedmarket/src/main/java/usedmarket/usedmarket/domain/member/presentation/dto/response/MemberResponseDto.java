package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.dto.response.ProductAllResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String imgPath;
    private String nickname;
    private int countProducts;
    private double mannerTemperature;
    private LocalDateTime createdDate;
    private int completeProductNumber;
    private List<ProductAllResponseDto> products;
    //거래 후기 댓글

    @Builder
    public MemberResponseDto(Member member) {
        this.imgPath = member.getImgPath();
        this.nickname = member.getNickname();
        this.countProducts = member.getProductList().size();
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
