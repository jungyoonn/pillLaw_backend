package com.eeerrorcode.pilllaw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.dto.product.ProductWithCategoryDto;
import com.eeerrorcode.pilllaw.service.product.ProductService;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController 
@RequestMapping("api/v1/product")
@Log4j2
public class ProductController {

  @Autowired
  private ProductService productService;

  // @Autowired
  // private CategoryService categoryService;
  
  // 포스트맨 통과!
  // @GetMapping("list")
  // public List<ProductDto> allList(@RequestParam(required = false) List<String> categoryNames) {
  //     log.info("showlist :::::::::::::::::::::::::::::::::::::::::::::: " + categoryNames);
  //     if (categoryNames != null && !categoryNames.isEmpty()) {
  //         return productService.listProductByCategoryNameList(categoryNames);
  //     }
  //     return productService.listAllProduct();
  // }
  

  // @GetMapping("list")
  // public List<CategoryDto> categoryList(@RequestParam CategoryType type) {
  //   log.info("listCategoryByType::::::::::::::::::::::::::::::::::::::::::::::::::::");
  //   return categoryService.listCategoryByType(type);
  // }
  

  // 포스트맨 통과!
  @GetMapping(value = "{pno}")
  public ResponseEntity<?> showDetail(@PathVariable("pno") Long pno) {
    log.info("showdetail::::::::::::::::::::::::::::::::::::::::::::::::::::");
    return ResponseEntity.ok(pno + "번 글 조회" + productService.viewProduct(pno));
    // try {
    //   return ResponseEntity.ok(CommonResponseDto
    //   .builder()
    //     .msg(productService.viewProduct(pno) + "조회 완료")
    //     .ok(true)
    //     .statusCode(HttpStatus.OK.value())
    //   .build());
    // } catch(Exception e){
    //   return ResponseEntity.badRequest().body(
    //     CommonResponseDto.builder()
    //       .msg("존재하지 않는 상품입니다. 다시 시도해 주세요.")
    //       .ok(false)
    //       .statusCode(HttpStatus.BAD_REQUEST.value())
    //       .build()
    //   );
    // }
  }

  // 포스트맨 통과!
  @PostMapping("")
  public ResponseEntity<?> register(@RequestBody ProductDto productDto) {
    log.info("register::::::::::::::::::::::::::::::::::::::::::::::::::::"); 
    productService.registerProduct(productDto);
    return new ResponseEntity<>(productDto.getPname() + "registered" ,HttpStatus.OK);
  }

  // 포스트맨 통과!
  @PutMapping(value = "/{pno}")
  public ResponseEntity<?> modify(@RequestBody ProductDto productDto) {
    log.info("modify::::::::::::::::::::::::::::::::::::::::::::::::::::"); 
    productService.modifyProduct(productDto);
    log.info(productDto);
    return new ResponseEntity<>(productDto.getPname() + "modified", HttpStatus.OK);
  }
  // 포스트맨 통과!
  @DeleteMapping(value = "/{pno}")
  public ResponseEntity<?> remove(@PathVariable("pno") Long pno){
    log.info("delete::::::::::::::::::::::::::::::::::::::::::::::::::::"); 
    productService.deleteProduct(pno);
    return new ResponseEntity<>(pno + "removed", HttpStatus.OK);
  }
  
  @GetMapping("/detail/{pno}")
  public ResponseEntity<?> getMethodName(@PathVariable("pno") Long pno) {
    log.info("delete::::::::::::::::::::::::::::::::::::::::::::::::::::"); 
    return ResponseEntity.ok(productService.viewProductUsingView(pno));
  }
  
  @GetMapping("/list")
  public List<ProductWithCategoryDto> allListWithCategory() {
    return productService.listAllProductWithCategory();
  }
  
  
}
