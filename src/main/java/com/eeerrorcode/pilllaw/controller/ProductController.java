package com.eeerrorcode.pilllaw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.dto.product.ProductDetailDto;
import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.dto.product.ProductRatingDto;
import com.eeerrorcode.pilllaw.dto.product.ProductWithCategoryDto;
import com.eeerrorcode.pilllaw.repository.order.OrderItemRepository;
import com.eeerrorcode.pilllaw.service.board.ProductDetailService;
import com.eeerrorcode.pilllaw.service.board.ProductReviewService;
import com.eeerrorcode.pilllaw.service.product.ProductPriceService;
import com.eeerrorcode.pilllaw.service.product.ProductService;
import com.eeerrorcode.pilllaw.service.s3.S3Service;

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

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private S3Service s3Service;

  // 포스트맨 통과!
  @GetMapping(value = "/{pno}", produces = "application/json")
  public ResponseEntity<?> showDetail(@PathVariable("pno") Long pno) {
    log.info("showDetail :::::::::::::::::::::::::::::::::::::::::::: {}", pno);

    Optional<ProductDto> optionalProduct = productService.viewProduct(pno);
    if (optionalProduct.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Product not found: " + pno);
    }
    ProductDto product = optionalProduct.get();
    log.info("product => {}", product);

    ProductDetailDto detail = null;
    try {
      detail = productDetailService.showDetailsByPno(pno);
      log.info("detail => {}", detail);
    } catch (NoSuchElementException e) {
      log.warn("No product details found for pno: {}", pno);
    }
    List<String> detailImage = s3Service.getDetailImages(pno);

    List<ProductReviewDto> reviews = productReviewService.showReviewsByProduct(pno);
    if (reviews == null) {
      reviews = new ArrayList<>();
    }
    log.info("reviews => {}", reviews);

    Map<String, Object> response = new HashMap<>();
    response.put("product", product);
    response.put("detail", detail);
    response.put("detailUrls", detailImage);
    response.put("reviews", reviews);

    return ResponseEntity.ok(response);
  }

  // 포스트맨 통과!
  @PostMapping("")
  public ResponseEntity<?> register(@RequestBody ProductDto productDto) {
    log.info("register::::::::::::::::::::::::::::::::::::::::::::::::::::");
    productService.registerProduct(productDto);
    return new ResponseEntity<>(productDto.getPname() + "registered", HttpStatus.OK);
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
  public ResponseEntity<?> remove(@PathVariable("pno") Long pno) {
    log.info("delete::::::::::::::::::::::::::::::::::::::::::::::::::::");
    productService.deleteProduct(pno);
    return new ResponseEntity<>(pno + "removed", HttpStatus.OK);
  }

  @GetMapping("/list")
  public List<ProductWithCategoryDto> allListWithCategory() {
    return productService.listAllProductWithCategory();
  }

  @GetMapping("/top-ordered-products")
  public ResponseEntity<List<Long>> getTopOrderedProducts() {
    List<Long> topProductPnos = orderItemRepository.findTopOrderedProducts(); // ✅ 상위 6개 pno 조회
    System.out.println("Top Ordered Product PNOs: " + topProductPnos);
    return ResponseEntity.ok(topProductPnos);
  }

  @GetMapping("/top-rated")
  public ResponseEntity<List<ProductRatingDto>> getTopRatedProducts() {
    log.info("제품 3개 조회 API 호출");
    List<ProductRatingDto> topRatedProducts = productService.getTopRatedProducts();
    return ResponseEntity.ok(topRatedProducts);
  }

}
