package usedmarket.usedmarket.domain.board.presentation.dto.request;

import lombok.Getter;
import usedmarket.usedmarket.domain.board.domain.BoardStatus;

@Getter
public class BoardStatusRequestDto {

    private BoardStatus status;

}
