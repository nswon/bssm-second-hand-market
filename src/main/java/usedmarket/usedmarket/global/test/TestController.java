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

    @GetMapping("/test")
    public String test() {
        return "Success Connection";
    }
}
