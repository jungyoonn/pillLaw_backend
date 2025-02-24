package com.eeerrorcode.pilllaw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.dto.product.ProductDetailDto;
import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.dto.product.ProductWithCategoryDto;
import com.eeerrorcode.pilllaw.service.board.ProductDetailService;
import com.eeerrorcode.pilllaw.service.board.ProductReviewService;
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

  @Autowired
  private ProductDetailService productDetailService;

  @Autowired
  private ProductReviewService productReviewService;

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
    log.info("showDetail::::::::::::::::::::::::::::::::::::::::");

    ProductDto product = productService.viewProduct(pno).orElse(null);
    if (product == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("cannot find detail");
    }

    ProductDetailDto detail = null;
    try {
        detail = productDetailService.showDetailsByPno(pno);
    } catch (NoSuchElementException e) {
        log.warn("Not product Details" + pno);
    }

    List<ProductReviewDto> reviews = productReviewService.showReviewsByProduct(pno);
    if (reviews == null) {
        reviews = new ArrayList<>(); 
    }

    Map<String, Object> response = new HashMap<>();
    response.put("product", product);
    response.put("detail", detail); 
    response.put("reviews", reviews);

    return ResponseEntity.ok(response);
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
  
  @GetMapping("/list")
  public List<ProductWithCategoryDto> allListWithCategory() {
    return productService.listAllProductWithCategory();
  }
  

  
}
