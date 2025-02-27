package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.file.FileType;
import com.eeerrorcode.pilllaw.service.file.FileService;
import com.eeerrorcode.pilllaw.service.s3.S3Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/file")
@Log4j2
public class S3FileController {

  private final S3Service s3Service;

  private final FileService fileService;

  @PostMapping("/upload")
  public ResponseEntity<List<FileDto>> uploadFiles(
      @RequestParam("files") List<MultipartFile> files,
      @RequestParam(value = "productDetailId", required = false) Long productDetailId,
      @RequestParam(value = "productReviewId", required = false) Long productReviewId,
      @RequestParam(value = "noticeId", required = false) Long noticeId   ) {
      if (Stream.of(productDetailId, productReviewId, noticeId).filter(Objects::nonNull).count() != 1) {
        return ResponseEntity.badRequest().body(null);
      }

      log.info("🔹 파일 업로드 요청 - productReviewId={}, productDetailId={}, noticeId={}", 
      productReviewId, productDetailId, noticeId);
    List<FileDto> uploadedFiles = new ArrayList<>();

    for (MultipartFile file : files){
      try{
        String origin = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();   
        String ext = getFileExtension(origin);
        String fileName = uuid + "." + ext;
        String mimeType = file.getContentType();

        if(productReviewId != null){
          String folderPath = "uploads/review/" + productReviewId + "/";
          String key = folderPath + fileName;
          String fileUrl = s3Service.uploadFile(file, key);
          FileDto fileDto = FileDto
          .builder()  
            .uuid(uuid)
            .origin(origin)
            .fname(fileName)
            .mime(mimeType)
            .path(folderPath)
            .url(fileUrl)
            .ext(ext)
            .size(file.getSize())
            .type(FileType.REVIEW)  // ✅ 파일 타입 설정
            .prno(productReviewId)  // ✅ 리뷰 번호 설정
          .build();
          fileService.saveFile(fileDto);
          uploadedFiles.add(fileDto);
        }
      } catch (Exception e){
        log.error("파일 업로드 실패: {}", e.getMessage());
      }
    }
    return ResponseEntity.ok(uploadedFiles);
  }

  @GetMapping("/download/{uuid}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable String uuid) {
    FileDto fileDto = fileService.getFileByUuid(uuid)
      .orElseThrow(() -> new NoSuchElementException("파일을 찾을 수 없습니다: " + uuid));

    byte[] fileContent = s3Service.fileDownload(fileDto.getPath() + "/" + fileDto.getFname());

    String contentDisposition = switch (fileDto.getType()) {
      case NOTICE -> "attachment; filename=\"공지사항_" + fileDto.getOrigin() + "\"";
      case REVIEW -> "attachment; filename=\"리뷰_" + fileDto.getOrigin() + "\"";
      case DETAIL -> "attachment; filename=\"제품세부_" + fileDto.getOrigin() + "\"";
      default -> "attachment; filename=\"" + fileDto.getOrigin() + "\"";
    };

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
      .contentType(MediaType.parseMediaType(fileDto.getMime()))
      .body(fileContent);
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
