package usedmarket.usedmarket.domain.member.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import usedmarket.usedmarket.domain.board.domain.BoardStatus;
import usedmarket.usedmarket.domain.board.presentation.dto.response.BoardAllResponseDto;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ManageCompleteResponseDto {

    private List<BoardAllResponseDto> boardCompleteResponseDtos;

    @Builder
    public ManageCompleteResponseDto(Member member) {
        this.boardCompleteResponseDtos = member.getBoardList().stream()
                .filter(board -> board.getBoardStatus().equals(BoardStatus.COMPLETE))
                .map(BoardAllResponseDto::new)
                .collect(Collectors.toList());
    }
}
