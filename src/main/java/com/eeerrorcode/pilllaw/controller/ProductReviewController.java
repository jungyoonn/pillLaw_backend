package com.eeerrorcode.pilllaw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.file.FileType;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.board.ProductReviewRepository;
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

  @Autowired
  private ProductReviewRepository productReviewRepository;

  @Autowired
  private MemberRepository memberRepository;

  // 포스트맨 통과!!
  @GetMapping(value="list/{pno}")
  public ResponseEntity<List<ProductReviewDto>> getListByProductReview(@PathVariable("pno")Long pno) {
    log.info("listByPno :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    log.info("📌 리뷰 요청: pno = {}", pno);
    log.info("📌 조회된 상품: {}", productService.viewProduct(pno).get().getPname());
    log.info("📌 상품 상태(isState): {}", productService.viewProduct(pno).get().isState());
    if(productService.viewProduct(pno).get().isState()){
      List<ProductReviewDto> reviewDtos = productReviewService.showReviewsByProduct(pno);
      return ResponseEntity.ok(reviewDtos);
    }
    log.error("❌ 400 Bad Request - 상품 상태가 비활성화됨: {}", pno);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  // 포스트맨 통과!!
  // @PostMapping("/register")
  // public ResponseEntity<?> register(@RequestBody ProductReviewDto dto) {
  //   log.info("Review register :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");      
  //   productReviewService.register(dto);
  //   return ResponseEntity.ok(dto.getContent());
  // }
  

  @PostMapping(value = "/register")
  public ResponseEntity<?> register(
      @RequestParam("pno") Long pno,
      @RequestParam("mno") Long mno,
      @RequestParam("content") String content,
      @RequestParam("rating") Integer rating,
      @RequestParam(value = "files", required = false) List<MultipartFile> files) {
  
      log.info("🔹 리뷰 등록 요청 - 상품 번호: {}, 작성자: {}, 평점: {}", pno, mno, rating);
  
      // 1️⃣ 리뷰 저장
      Long reviewId = productReviewService.register(
          ProductReviewDto.builder()
              .pno(pno)
              .mno(mno)
              .nickName(Member.builder().nickname(memberRepository.findById(mno).get().getNickname()).build().toString())
              .content(content)
              .rating(rating)
              .build()
      );
  
      log.info("✅ 리뷰 등록 완료 - reviewId={}", reviewId);
  
      // 2️⃣ 파일 업로드 처리
      List<FileDto> uploadedFiles = new ArrayList<>();
      if (files != null && !files.isEmpty()) {
          for (MultipartFile file : files) {
              try {
                  String key = "uploads/review/" + pno + "/" + reviewId + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
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
                      .type(FileType.REVIEW)  // ✅ 리뷰 타입으로 설정
                      .prno(reviewId)  // ✅ 리뷰 ID 설정
                      .build();
  
                  fileService.saveFile(fileDto);
                  uploadedFiles.add(fileDto);
              } catch (Exception e) {
                  log.error("파일 업로드 실패: {}", e.getMessage());
              }
          }
      }
  
      return ResponseEntity.ok(Map.of("reviewId", reviewId, "files", uploadedFiles));
  }
  
  
  
  @GetMapping("/mine/{mno}")
public ResponseEntity<List<ProductReviewDto>> getMyReviews(@PathVariable("mno") Long mno) {
    log.info("📢 내가 쓴 리뷰 요청: mno = {}", mno);

    List<ProductReviewDto> reviews = productReviewService.getReviewsByMember(mno);

    if (reviews.isEmpty()) {
        log.warn("⚠ 내가 쓴 리뷰 없음: mno = {}", mno);
        return ResponseEntity.noContent().build();
    }

    log.info("✅ 내가 쓴 리뷰 개수: {}개", reviews.size());
    return ResponseEntity.ok(reviews);
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

  @GetMapping("/popular")
    public ResponseEntity<List<ProductReviewDto>> getPopularReviews() {
        log.info("인기리뷰");
        List<ProductReviewDto> popularReviews = productReviewService.getPopularReviews();
        return ResponseEntity.ok(popularReviews);
  } 
  
}
