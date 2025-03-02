package com.eeerrorcode.pilllaw.controller;

import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.service.product.ProductFavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductFavoriteController {

  private final ProductFavoriteService productFavoriteService;

  @PostMapping("/{pno}/like")
  public ResponseEntity<?> likeProduct(@PathVariable Long pno, @RequestParam Long mno) {
    boolean result = productFavoriteService.likeProduct(mno, pno);
    return result
        ? ResponseEntity.ok("좋아요가 등록되었습니다.")
        : ResponseEntity.badRequest().body("이미 좋아요를 눌렀습니다.");
  }

  @PostMapping("/{pno}/unlike")
  public ResponseEntity<?> unlikeProduct(@PathVariable Long pno, @RequestParam Long mno) {
    boolean result = productFavoriteService.unlikeProduct(mno, pno);
    return result
        ? ResponseEntity.ok("좋아요가 취소되었습니다.")
        : ResponseEntity.badRequest().body("좋아요한 기록이 없습니다.");
  }

  @GetMapping("/{pno}/isLiked")
  public ResponseEntity<Boolean> isProductLikedByUser(@PathVariable Long pno, @RequestParam Long mno) {
    boolean isLiked = productFavoriteService.isProductLikedByUser(mno, pno);
    return ResponseEntity.ok(isLiked);
  }

  @GetMapping("/{pno}/likes")
  public ResponseEntity<Long> countProductLikes(@PathVariable Long pno) {
    long likeCount = productFavoriteService.countProductLikes(pno);
    return ResponseEntity.ok(likeCount);
  }

  @GetMapping("/{mno}/liked")
  public ResponseEntity<List<ProductDto>> getLikedProducts(@PathVariable Long mno) {
      List<ProductDto> likedProducts = productFavoriteService.getLikedProducts(mno);
      return likedProducts.isEmpty()
              ? ResponseEntity.noContent().build()
              : ResponseEntity.ok(likedProducts);
  }
}
