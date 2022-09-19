package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String imgUrl;
    private String nickname;
    private int countProducts;
    private double mannerTemperature;
    private LocalDateTime createdDate;
    private int completeProductNumber;

    @Builder
    public MemberResponseDto(Member member) {
        this.imgUrl = member.getImgUrl();
        this.nickname = member.getNickname();
        this.countProducts = member.getProductList().size();
        this.mannerTemperature = member.getMannerTemperature();
        this.createdDate = member.getCreatedDate();
        this.completeProductNumber = CompleteProductsResponseDto.builder()
                .member(member)
                .build()
                .getBoardCompleteResponseDtos().size();
    }
}
