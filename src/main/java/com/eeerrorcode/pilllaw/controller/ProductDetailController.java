package com.eeerrorcode.pilllaw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.product.ProductDetailDto;
import com.eeerrorcode.pilllaw.service.board.ProductDetailService;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@Log4j2
@RequestMapping("api/v1/product/detail")
public class ProductDetailController {
  
  @Autowired
  private ProductDetailService productDetailService;

  // 포스트맨 통과 !!!
  // @GetMapping(value = "{pno}")
  // public ResponseEntity<?> viewDetail(@PathVariable("pno")Long pno) {
  //   log.info("showDetail ::::::::::::::::::::::::::::::::::::::::::::::::");
  //     return ResponseEntity.ok(pno + "상품의 상세정보를 조회합니다." + productDetailService.showDetailsByPno(pno));
  // }
  

  // 포스트맨 통과 !!!
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody ProductDetailDto dto) {
    log.info("register ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    productDetailService.registerDetail(dto);
    return new ResponseEntity<>(dto.getPno() + "번에 등록되었습니다.", HttpStatus.OK);
  }

  // 포스트맨 통과 !!!
  @PutMapping(value = "{pno}")
  public ResponseEntity<?> modify(@RequestBody ProductDetailDto dto) {
    log.info("modify ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    productDetailService.modifyDetail(dto);
    return new ResponseEntity<>(dto.getPdno() + "번 상세정보 수정", HttpStatus.OK);
    
  }

  // 포스트맨 통과 !!!
  @DeleteMapping(value = "{pdno}")
  public ResponseEntity<?> delete(@PathVariable("pdno")Long pdno){
    log.info("delete ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    productDetailService.deleteDetail(pdno);
    return new ResponseEntity<>(pdno + "번 상세정보 삭제", HttpStatus.OK);

  }
  
  // 포스트맨 통과 !!!
  @GetMapping("/listall")
  public ResponseEntity<?> list(@PageableDefault(size = 10, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {
    log.info("list ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    Page<ProductDetailDto> returnList = productDetailService.showAll(pageable);
    return ResponseEntity.ok(returnList);
  }
  

}
