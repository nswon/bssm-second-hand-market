package usedmarket.usedmarket.domain.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.board.domain.BoardStatus;
import usedmarket.usedmarket.domain.board.presentation.dto.request.BoardStatusRequestDto;
import usedmarket.usedmarket.domain.board.presentation.dto.response.BoardAllResponseDto;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.domain.board.domain.Board;
import usedmarket.usedmarket.domain.board.domain.BoardRepository;
import usedmarket.usedmarket.domain.board.presentation.dto.request.BoardRequestDto;
import usedmarket.usedmarket.domain.board.presentation.dto.response.BoardDetailResponseDto;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {

    @Value("${file.dir}")
    private String fileDir;

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createBoard(BoardRequestDto requestDto) throws IOException {
        if(requestDto.getFile().isEmpty()) {
            throw new IllegalArgumentException("이미지가 들어오지 않았습니다.");
        }

        String originFilename = requestDto.getFile().getOriginalFilename();

        String saveFilename = UUID.randomUUID() + "_" + originFilename;

        File save = new File(fileDir + saveFilename);
        requestDto.getFile().transferTo(save);

        Board board = Board.builder()
                .title(requestDto.getTitle())
                .imgName(saveFilename)
                .imgPath(fileDir + saveFilename)
                .price(requestDto.getPrice())
                .content(requestDto.getContent())
                .build();

        board.confirmWriter(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요.")));

        boardRepository.save(board);
        board.addSaleBoard();
        return board.getId();
    }

    public List<BoardAllResponseDto> findAllBoard() {
        return boardRepository.findAll().stream()
                .filter(board -> !board.getBoardStatus().equals(BoardStatus.COMPLETE))
                .map(BoardAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public BoardDetailResponseDto detailBoard(Long id) {
        return boardRepository.findById(id)
                .map(BoardDetailResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));
    }

    @Transactional
    public BoardDetailResponseDto statusBoard(Long id, BoardStatusRequestDto requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        board.updateStatus(requestDto.getStatus());
        return BoardDetailResponseDto.builder()
                .board(board)
                .build();
    }

    public List<BoardAllResponseDto> searchBoard(String keyword) {
        return boardRepository.findByTitleContaining(keyword).stream()
                .map(BoardAllResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateBoard(Long id, BoardRequestDto requestDto) throws IOException {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        if(!board.getWriter().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 판매글은 수정할 수 없습니다.");
        }

        String saveFilename = "";

        if(requestDto.getFile().isEmpty()) {
            //만약에 비어있다면 원래 이미지를 저장
            saveFilename = board.getImgPath();
        }
        else {
            String originFilename = requestDto.getFile().getOriginalFilename();

            saveFilename = UUID.randomUUID() + "_" + originFilename;

            File save = new File(fileDir + saveFilename);
            requestDto.getFile().transferTo(save);
        }

        board.update(requestDto.getTitle(),
                       saveFilename,
                fileDir + saveFilename,
                        requestDto.getPrice(),
                        requestDto.getContent()
        );

        return board.getId();
    }

    @Transactional
    public Long deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        if(!board.getWriter().getEmail().equals(SecurityUtil.getLoginUserEmail())) {
            throw new IllegalArgumentException("다른 사용자의 판매글은 삭제할 수 없습니다.");
        }

        File file = new File(board.getImgPath());
        file.delete();

        boardRepository.delete(board);
        return board.getId();
    }
}
