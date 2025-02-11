package com.eeerrorcode.pilllaw.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/api")
@Log4j2
public class hadminTestController {

  // /api/uploads 경로
  // c 드라이브에 폴더 생성 후 
    private final String uploadDir = "c:/uploads/";

    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 저장할 폴더가 없으면 생성
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 파일 저장
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File destination = new File(uploadDir + fileName);
            file.transferTo(destination);

            // 로컬 서버에서 접근할 수 있는 URL 생성
            String fileUrl = "/uploads/" + fileName;

            // 응답 반환
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "파일 업로드 실패"));
        }
    }
}
