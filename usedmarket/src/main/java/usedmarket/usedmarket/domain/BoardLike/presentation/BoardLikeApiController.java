package usedmarket.usedmarket.domain.BoardLike.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.BoardLike.service.BoardLikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class BoardLikeApiController {

    private final BoardLikeService boardLikeService;

    @PutMapping("/{id}")
    public void like(@PathVariable("id") Long id) {
        boardLikeService.like(id);
    }

}
