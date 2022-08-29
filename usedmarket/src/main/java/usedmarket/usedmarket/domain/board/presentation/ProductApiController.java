package usedmarket.usedmarket.domain.board.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.board.presentation.dto.request.BoardRequestDto;
import usedmarket.usedmarket.domain.board.presentation.dto.response.BoardResponseDto;
import usedmarket.usedmarket.domain.board.service.BoardService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductApiController {

    private final BoardService boardService;

    @PostMapping("/create")
    public Long createBoard(BoardRequestDto requestDto) throws IOException {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/findAll")
    public List<BoardResponseDto> findAllBoard() {
        return boardService.findAllBoard();
    }

    @GetMapping("/find/{id}")
    public BoardResponseDto detailBoard(@PathVariable("id") Long id) {
        return boardService.detailBoard(id);
    }

    @GetMapping("/search")
    public List<BoardResponseDto> searchBoard(@RequestParam("keyword") String keyword) {
        return boardService.searchBoard(keyword);
    }

    @PutMapping("/update/{id}")
    public Long updateBoard(@PathVariable("id") Long id, BoardRequestDto requestDto) throws IOException {
        return boardService.updateBoard(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteBoard(@PathVariable("id") Long id) {
        return boardService.deleteBoard(id);
    }
}
