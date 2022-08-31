package usedmarket.usedmarket.domain.BoardLike.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.BoardLike.service.BoardLikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class BoardLikeApiController {

    private final BoardLikeService boardLikeService;

    @PutMapping("/{id}/like")
    public void like(@PathVariable("id") Long id) {
        boardLikeService.like(id);
    }

    @DeleteMapping("/{id}/like")
    public void unLike(@PathVariable("id") Long id) {
        boardLikeService.anLike(id);
    }
}
