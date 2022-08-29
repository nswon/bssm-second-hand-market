package usedmarket.usedmarket.domain.comment.presentation.dto.request;

import lombok.Getter;
import usedmarket.usedmarket.domain.comment.domain.Comment;

@Getter
public class CommentRequestDto {

    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}
