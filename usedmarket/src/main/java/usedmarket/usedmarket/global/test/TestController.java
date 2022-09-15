package usedmarket.usedmarket.global.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/test")
    public String test() {
        return "Success Connection";
    }

    @PostMapping("/upload")
    public void upload(TestDto dto) throws IOException {

        for(MultipartFile file : dto.getFile()) {
            String originFilename = file.getOriginalFilename();
            String saveFilename = UUID.randomUUID() + "_" + originFilename;
            File save = new File(fileDir + saveFilename);
            file.transferTo(save);
        }

        log.info("테스트 가격입니다." + dto.getPrice());
    }
}
