package usedmarket.usedmarket.global.file;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileResponseDto {

    private String imgPath;
    private String getImgUrl;

    @Builder
    public FileResponseDto(String imgPath, String getImgUrl) {
        this.imgPath = imgPath;
        this.getImgUrl = getImgUrl;
    }
}
