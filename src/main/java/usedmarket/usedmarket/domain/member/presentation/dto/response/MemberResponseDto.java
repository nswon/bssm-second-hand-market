package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String imgUrl;
    private String nickname;
    private int countProducts;
    private double mannerTemperature;
    private LocalDateTime createdDate;
    private int completeProductNumber;
    private List<MemberProductResponseDto> products;

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
                .getCompleteProducts().size();
        this.products = member.getProductList().stream()
                .map(MemberProductResponseDto::new)
                .collect(Collectors.toList());
    }
}
