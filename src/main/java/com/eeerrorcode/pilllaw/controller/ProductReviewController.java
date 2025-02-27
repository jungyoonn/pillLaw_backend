package com.eeerrorcode.pilllaw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.file.FileType;
import com.eeerrorcode.pilllaw.service.board.ProductReviewService;
import com.eeerrorcode.pilllaw.service.file.FileService;
import com.eeerrorcode.pilllaw.service.product.ProductService;
import com.eeerrorcode.pilllaw.service.s3.S3Service;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("api/v1/product/detail/review")
@Log4j2
public class ProductReviewController {
  
  @Autowired
  private ProductReviewService productReviewService;

  @Autowired
  private ProductService productService;

  @Autowired
  private FileService fileService;

  @Autowired
  private S3Service s3Service;

  // 포스트맨 통과!!
  @GetMapping(value="list/{pno}")
  public ResponseEntity<List<ProductReviewDto>> getListByProductReview(@PathVariable("pno")Long pno) {
    log.info("listByPno :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

    if(productService.viewProduct(pno).get().isState()){
      List<ProductReviewDto> reviewDtos = productReviewService.showReviewsByProduct(pno);
      return ResponseEntity.ok(reviewDtos);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  // 포스트맨 통과!!
  // @PostMapping("/register")
  // public ResponseEntity<?> register(@RequestBody ProductReviewDto dto) {
  //   log.info("Review register :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");      
  //   productReviewService.register(dto);
  //   return ResponseEntity.ok(dto.getContent());
  // }
  

  // 포스트맨 통과!!
  @PostMapping(value = "/register", consumes = "multipart/form-data")
  public ResponseEntity<?> register(
    @RequestParam("pno") Long pno,
    @RequestParam("mno") Long mno,
    @RequestParam("content") String content,
    @RequestParam("rating") Integer rating,
    @RequestParam(value = "files", required = false) List<MultipartFile> files) {
    log.info("리뷰 등록 요청 - 상품 번호: {}, 작성자: {}, 평점: {}", pno, mno, rating);
    List<FileDto> uploadedFiles = new ArrayList<>();
    if (files != null && !files.isEmpty()) {
        for (MultipartFile file : files) {
            try {
              String key = "uploads/review/" + pno + UUID.randomUUID() + "_" + file.getOriginalFilename();
              String fileUrl = s3Service.uploadFile(file, key); 
              FileDto fileDto = FileDto.builder()
                      .uuid(UUID.randomUUID().toString())
                      .origin(file.getOriginalFilename())
                      .fname(file.getOriginalFilename())
                      .mime(file.getContentType())
                      .path(key)
                      .url(fileUrl)
                      .ext(getFileExtension(file.getOriginalFilename()))
                      .size(file.getSize())
                      .type(FileType.REVIEW) 
                      .build();
              
              log.info("파일 DTO 생성 완료: {}", fileDto);  // ✅ DTO 값 확인

              uploadedFiles.add(fileDto);
            } catch (Exception e) {
                log.error("파일 업로드 실패: {}", e.getMessage());
            }
        }
    }

    ProductReviewDto reviewDto = ProductReviewDto.builder()
            .pno(pno)
            .mno(mno)
            .content(content)
            .rating(rating)
            .fileDtos(uploadedFiles)
            .build();

    log.info("리뷰 DTO 생성 완료: {}", reviewDto);

    try{
      Long reviewId = productReviewService.register(reviewDto);
      if (reviewId == null) {
        log.error("리뷰 등록 실패: reviewId가 NULL");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 ID가 없습니다.");
      }

      String jsonResponse = "{\"reviewId\": " + reviewId + "}";
      log.info("서버 응답: {}", jsonResponse);
      
      // ✅ `saveFile`이 실행되는지 확인하기 위해 로깅 추가
      if(!uploadedFiles.isEmpty()){
        uploadedFiles.forEach(file -> {
        log.info("파일 저장 요청: {}", file);
        fileService.saveFile(file);
        });
      }
      return ResponseEntity.ok().body(jsonResponse);
    }catch (Exception e) {
      log.error(" 리뷰 등록 중 오류 발생: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 등록 중 오류 발생!");
    }
  }
  

  private String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
        throw new IllegalArgumentException("파일 확장자가 없습니다: " + fileName);
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }
  
  // 포스트맨 통과!!!
  @DeleteMapping(value = "/{prno}")
  public ResponseEntity<?> remove(@PathVariable("prno") Long prno){
    log.info("Review modify :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");      
    productReviewService.delete(prno);
    return ResponseEntity.ok(prno + "deleted");
  }
  
}
