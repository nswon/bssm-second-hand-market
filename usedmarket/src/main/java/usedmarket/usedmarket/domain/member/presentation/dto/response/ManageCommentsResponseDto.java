package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.comment.presentation.dto.response.CommentResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.products.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ManageCommentsResponseDto {

    private final List<CommentResponseDto> commentResponseDtos;

    @Builder
    public ManageCommentsResponseDto(Member member) {
        this.commentResponseDtos = member.getCommentList(member.getProductList()).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
