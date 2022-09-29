package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.comment.presentation.dto.response.CommentResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductCommentsResponseDto {

    private final List<CommentResponseDto> memberComments;

    @Builder
    public ProductCommentsResponseDto(Member member) {
        this.memberComments = member.getCommentList(member.getProductList()).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
