package usedmarket.usedmarket.domain.comment.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.comment.presentation.dto.request.CommentRequestDto;
import usedmarket.usedmarket.domain.comment.presentation.dto.response.CommentResponseDto;
import usedmarket.usedmarket.domain.comment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/{id}/new")
    public Long createComment(@PathVariable("id") Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(id, requestDto);
    }

    @GetMapping("/")
    public List<CommentResponseDto> findAllComment() {
        return commentService.findAllComment();
    }
}
