package com.eeerrorcode.pilllaw.service.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.repository.board.ProductReviewRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductReviewServiceTests {

  @Autowired
  private ProductReviewService productReviewService;

  @Autowired
  private ProductReviewRepository productReviewRepository;

  @Test
  @Transactional
  public void testShowReviews(){
    log.info(productReviewService.showReviews());
  }
  
  @Test
  @Transactional
  @Rollback(false)
  public void testRegister(){
    productReviewService.register(
      ProductReviewDto
      .builder()
        .content("test register 콘텐트 수정예정")
        .mno(2L)
        .pno(9L)
        .rating(4)
      .build()
    );
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testDelete(){
    productReviewService.delete(1L);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testModify(){
    // productReviewService.modify(productReviewService.toDto(productReviewRepository.findById(2L).get()).builder().build(););
    productReviewService.modify(ProductReviewDto.builder().prno(2L).content("수정된 콘텍스트").rating(5).build());

  }

  @Test
  @Transactional
  public void testListByPno(){
    productReviewService.showReviewsByProduct(9L);
  }
}
