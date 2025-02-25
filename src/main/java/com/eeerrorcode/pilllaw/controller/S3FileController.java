package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.service.file.FileService;
import com.eeerrorcode.pilllaw.service.s3.S3Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@AllArgsConstructor
@RequestMapping("/api/file")
@Log4j2
public class S3FileController {

  private final S3Service s3Service;

  private final FileService fileService;

  @PostMapping("/upload")
  public ResponseEntity<List<FileDto>> postMethodName(@RequestParam("files") List<MultipartFile> files) {
    List<FileDto> uploadedFiles = new ArrayList<>();

    for (MultipartFile file : files){
      try{
        String origin = file.getOriginalFilename();
        String path = getUploadPath();
        String uuid = UUID.randomUUID().toString();   
        String ext = getFileExtension(origin);
        String fileName = uuid + "." + ext;
        String mimeType = file.getContentType();
        byte[] content = file.getBytes();
        String key = "uploads" + path + "/" + fileName;

        String fileUrl = s3Service.uploadFile(file, key);
        
        FileDto fileDto = FileDto
        .builder()
          .uuid(uuid)
          .origin(origin)
          .fname(fileName)
          .mime(mimeType)
          .path(path)
          .url(fileUrl)
          .ext(ext)
          .size(file.getSize())
        .build();
        fileService.saveFile(fileDto);

        uploadedFiles.add(fileDto);
      } catch (Exception e){
        log.error("파일 업로드 실패: {}", e.getMessage());
      }

    }
    return null;
  }
  
  private String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
        throw new IllegalArgumentException("파일 확장자를 찾을 수 없음: " + fileName);
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  private String getUploadPath() {
      return new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
  }
}
