package usedmarket.usedmarket.domain.comment.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import usedmarket.usedmarket.domain.comment.domain.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private String nickname;
    private LocalDateTime createdDate;
    private String content;

    @Builder
    public CommentResponseDto(Comment comment) {
        this.nickname = comment.getWriter().getNickname();
        this.createdDate = comment.getCreatedDate();
        this.content = comment.getContent();
    }
}
