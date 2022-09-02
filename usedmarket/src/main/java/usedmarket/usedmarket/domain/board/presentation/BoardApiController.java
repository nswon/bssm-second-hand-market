package usedmarket.usedmarket.domain.board.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usedmarket.usedmarket.domain.board.presentation.dto.request.BoardRequestDto;
import usedmarket.usedmarket.domain.board.presentation.dto.request.BoardStatusRequestDto;
import usedmarket.usedmarket.domain.board.presentation.dto.response.BoardAllResponseDto;
import usedmarket.usedmarket.domain.board.presentation.dto.response.BoardDetailResponseDto;
import usedmarket.usedmarket.domain.board.service.BoardService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/new")
    public Long createBoard(BoardRequestDto requestDto) throws IOException {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/")
    public List<BoardAllResponseDto> findAllBoard() {
        return boardService.findAllBoard();
    }

    @GetMapping("/{id}")
    public BoardDetailResponseDto detailBoard(@PathVariable("id") Long id) {
        return boardService.detailBoard(id);
    }

    @PutMapping("/{id}")
    public BoardDetailResponseDto statusBoard(@PathVariable("id") Long id, @RequestBody BoardStatusRequestDto requestDto) {
        return boardService.statusBoard(id, requestDto);
    }

    @GetMapping("/search")
    public List<BoardAllResponseDto> searchBoard(@RequestParam("keyword") String keyword) {
        return boardService.searchBoard(keyword);
    }

    @PutMapping("/{id}/edit")
    public Long updateBoard(@PathVariable("id") Long id, BoardRequestDto requestDto) throws IOException {
        return boardService.updateBoard(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteBoard(@PathVariable("id") Long id) {
        return boardService.deleteBoard(id);
    }
}
