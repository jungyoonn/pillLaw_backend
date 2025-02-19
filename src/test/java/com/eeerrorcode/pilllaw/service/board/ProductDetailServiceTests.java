package com.eeerrorcode.pilllaw.service.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.product.ProductDetailDto;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class ProductDetailServiceTests {

  @Autowired
  private ProductDetailService productDetailService;

  @Test
  @Transactional
  @Rollback(false)
  public void testRegister(){
    ProductDetailDto dto = ProductDetailDto
    .builder()
      .content("Test Register Notice2")
      .mno(6L)
      .pno(113L)
      .count(0L)
    .build();
    productDetailService.registerDetail(dto);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testViewByPno()  {
    productDetailService.showDetailsByPno(117L);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testShowAll(){
    Pageable pageable = PageRequest.of(0, 5, Sort.by("regDate").descending());
    Page<ProductDetailDto> retrunResult = productDetailService.showAll(pageable);

    retrunResult.getContent().forEach(n->log.info(n.getContent()));
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testDelete(){
    productDetailService.deleteDetail(1L);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testModify(){
    ProductDetailDto returnDto = ProductDetailDto
    .builder()
      .pdno(2L)
      .content("Test 쑤쩡완료뙨끌이ㅃ니따")
      .count(0L)
      .pno(113L)
      .mno(6L)
    .build();

    productDetailService.modifyDetail(returnDto);
  }
  
}
