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
    private List<ProductAllResponseDto> productAllResponseDtos;

    //구매내역
    //찜한 게시글
    //키워드 알림
    @Builder
    public MyInfoResponseDto(Member member) {
        this.imgPath = member.getImgPath();
        this.nickname = member.getNickname();
        this.mannerTemperature = member.getMannerTemperature();
        this.createdDate = member.getCreatedDate();
        this.productAllResponseDtos = member.getProductList().stream()
                    .map(ProductAllResponseDto::new)
                    .collect(Collectors.toList());
        }
}
