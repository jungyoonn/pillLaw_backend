package com.eeerrorcode.pilllaw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.service.board.ProductReviewService;
import com.eeerrorcode.pilllaw.service.product.ProductService;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("api/v1/product/detail/review")
@Log4j2
public class ProductReviewController {
  
  @Autowired
  private ProductReviewService productReviewService;

  @Autowired
  private ProductService productService;

  // 포스트맨 통과!!
  @GetMapping(value="list/{pno}")
  public ResponseEntity<List<ProductReviewDto>> getListByProductReview(@PathVariable("pno")Long pno) {
    log.info("listByPno :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

    if(!productService.viewProduct(pno).get().isState()){
      List<ProductReviewDto> reviewDtos = productReviewService.showReviewsByProduct(pno);
      return ResponseEntity.ok(reviewDtos);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  // 포스트맨 통과!!
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody ProductReviewDto dto) {
    log.info("Review register :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");      
    productReviewService.register(dto);
    return ResponseEntity.ok(dto.getContent());
  }
  

  // 포스트맨 통과!!
  @PutMapping(value = "/{prno}")
  public ResponseEntity<?> modify(@RequestBody ProductReviewDto dto) {
    log.info("Review modify :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");      
    productReviewService.modify(dto);
    log.info(dto.toString());
    return ResponseEntity.ok(dto);
  }
  
  // 포스트맨 통과!!!
  @DeleteMapping(value = "/{prno}")
  public ResponseEntity<?> remove(@PathVariable("prno") Long prno){
    log.info("Review modify :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");      
    productReviewService.delete(prno);
    return ResponseEntity.ok(prno + "deleted");
  }
  
}
