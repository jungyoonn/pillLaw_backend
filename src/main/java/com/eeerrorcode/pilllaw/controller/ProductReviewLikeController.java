package com.eeerrorcode.pilllaw.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewLikeDto;
import com.eeerrorcode.pilllaw.service.board.ProductReviewService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/product/review/like")
@AllArgsConstructor
@Log4j2
public class ProductReviewLikeController {

  private final ProductReviewService productReviewLikeService;

  // ✅ 특정 회원이 특정 리뷰를 좋아요 눌렀는지 확인
  @GetMapping("/check/{mno}/{prno}")
  public ResponseEntity<Boolean> checkIfLiked(@PathVariable("mno") Long mno, @PathVariable("prno") Long prno) {
    log.info("📌 좋아요 상태 확인 요청 - mno={}, prno={}", mno, prno);
    boolean isLiked = productReviewLikeService.isLikedByMember(mno, prno);
    return ResponseEntity.ok(isLiked);
  }

  // ✅ 좋아요 추가
  @PostMapping("/add")
  public ResponseEntity<Map<String, Object>> addLike(@RequestBody ProductReviewLikeDto dto) {
    log.info("✅ 좋아요 추가 요청 - mno={}, prno={}", dto.getMno(), dto.getPrno());
    productReviewLikeService.addLike(dto);

    // 추가된 좋아요 개수 반환
    Long likeCount = productReviewLikeService.countLikes(dto.getPrno());
    Map<String, Object> response = new HashMap<>();
    response.put("message", "좋아요 추가 완료");
    response.put("likeCount", likeCount);

    return ResponseEntity.ok(response);
  }

  // ✅ 좋아요 제거
  @PostMapping("/remove")
  public ResponseEntity<String> removeLike(@RequestBody ProductReviewLikeDto dto) {
    log.info("❌ 좋아요 취소 요청 - mno={}, prno={}", dto.getMno(), dto.getPrno());
    productReviewLikeService.removeLike(dto);
    return ResponseEntity.ok("좋아요 취소 완료");
  }

  @GetMapping("/count/{prno}")
  public ResponseEntity<Long> countLikes(@PathVariable("prno") Long prno) {
    log.info("📌 좋아요 개수 요청 - prno={}", prno);
    Long likeCount = productReviewLikeService.countLikes(prno);
    return ResponseEntity.ok(likeCount);
  }
}
