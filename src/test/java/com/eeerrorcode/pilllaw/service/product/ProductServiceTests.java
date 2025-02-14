package com.eeerrorcode.pilllaw.service.product;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.repository.product.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class ProductServiceTests {
  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;

  @Test
  @DisplayName("상품 등록 테스트 / ProductRegister Test")
  @Rollback(false)
  @Transactional
  public void testRegisterProduct(){
    
    ProductDto dto = ProductDto
      .builder()
        .pname("TEST 영양제")
        .company("TEST 제조사")
        .bestBefore(LocalDateTime.now().plusYears(2))
        .keep("TEST 보관법")
        .effect("TEST 효과")
        .precautions("TEST 복용 전 유의사항")
        .type(List.of("NORMAL", "EARLYBIRD"))
      .build();



    log.info("========================================");
    log.info("새 상품 등록 ::: " + dto.toString());
    log.info("========================================");

    productService.registerProduct(dto);
  }

}
