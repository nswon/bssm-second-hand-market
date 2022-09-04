package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.products.presentation.dto.response.ProductAllResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String imgPath;
    private String nickname;
    private int countBoard;
    private double mannerTemperature;
    private List<ProductAllResponseDto> productAllResponseDtos;
    //거래 후기 댓글

    @Builder
    public MemberResponseDto(Member member) {
        this.imgPath = member.getImgPath();
        this.nickname = member.getNickname();
        this.countBoard = member.getProductList().size();
        this.mannerTemperature = member.getMannerTemperature();
        this.productAllResponseDtos = member.getProductList().stream().map(ProductAllResponseDto::new).collect(Collectors.toList());
    }
}
