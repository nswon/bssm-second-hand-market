package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberLikeProductResponseDto {

    List<LikeProductResponseDto> likeProducts;

    @Builder
    public MemberLikeProductResponseDto(Member member) {
        likeProducts = member.getProductLikeList().stream()
                .map(LikeProductResponseDto::new)
                .collect(Collectors.toList());
    }
}
