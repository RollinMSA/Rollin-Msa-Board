package com.rollin.boardService.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
public class UploadController {
    @Value("${file}")
    private String filePath;

    @PostMapping("/upload")
    public ResponseEntity saveImg(@RequestBody MultipartFile file) throws IOException {
        log.info("들어왔다");
        log.info("hihi"+String.valueOf(file));
        if(!file.isEmpty()){
            String downloadPath=filePath+"/img/"+file.getOriginalFilename();
            file.transferTo(new File(downloadPath));
            return ResponseEntity.status(HttpStatus.OK).body("/img/"+file.getOriginalFilename());

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file is empty");
    }
}
