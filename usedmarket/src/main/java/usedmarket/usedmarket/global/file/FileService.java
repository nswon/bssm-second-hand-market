package usedmarket.usedmarket.global.file;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String imgPath = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(fileDir + imgPath));
        return fileDir + imgPath;
    }

    public void deleteFile(String imgPath) {
        File file = new File(imgPath);
        file.delete();
    }
}
