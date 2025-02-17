package com.eeerrorcode.pilllaw.service.product;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.repository.product.ProductCategoryRepository;
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

  @Autowired
  private ProductCategoryRepository productCategoryRepository;

  @Test
  @DisplayName("상품 등록 테스트 / ProductRegister Test")
  @Rollback(false)
  @Transactional
  public void testRegisterProduct(){
    
    ProductDto dto = ProductDto
      .builder()
        .pname("TEST 패키지 영양제 수정예정")
        .company("TEST 제조사")
        .bestBefore("구매일로부터 2년")
        .keep("TEST 보관법")
        .effect("TEST 효과")
        .precautions("TEST 복용 전 유의사항")
        .type(List.of("EARLYBIRD", "SUBONLY"))
      .build();

    log.info("========================================");
    log.info("새 상품 등록 ::: " + dto.toString());
    log.info("========================================");

    productService.registerProduct(dto);
  }

  @Test
  @DisplayName("상품 리스트 테스트 / ProductList Test")
  public void testListAllProduct(){
    log.info("========================================");
    log.info(productService.listAllProduct());
    log.info("========================================");
  }
  
  @Test
  @DisplayName("상품 개별 조회 테스트 / ProductView Test")
  public void testViewProduct(){
    Long pno = 10L;
    log.info("========================================");
    log.info(productService.viewProduct(pno));
    log.info("========================================");
  }

  @Test
  @DisplayName("상품 개별 삭제 테스트 / ProductView Test")
  @Rollback(false)
  public void testDeleteProduct(){
    Long pno = 15L;
    log.info("========================================");
    log.info("삭제 동작시작");
    productCategoryRepository.deleteByProductPno(pno);
    productService.deleteProduct(pno);
    log.info("삭제 동작완료");
    log.info("========================================");
  }

  @Test
  @DisplayName("상품 수정 테스트 / ProductModifyTest")
  @Rollback(false)
  public void testModify(){
    log.info("========================================");
    productService.modifyProduct(ProductDto
    .builder()
      .pno(13L)
      .pname("Pillaw Sample 영양제")
      .company("Pillaw Sample 제조사")
      .bestBefore("구매일로부터 2년")
      .keep("Pillaw Sample 보관법")
      .effect("Pillaw Sample 효과")
      .precautions("Pillaw Sample 복용 전 유의사항")
      .type(List.of("NORMAL"))
    .build());
    log.info("========================================");
    log.info(productService.viewProduct(15L));
  }

  @Test
  @DisplayName("카테고리로 상품 조회 리스트 테스트 / CategoryFindByProductList")
  public void testListProductByCategoryName(){
    productService.listProductByCategoryName("피로");
  }

  @Test
  @DisplayName("카테고리타입 + 카테고리 이름으로 상품 조회 리스트 테스트 / CategoryFindByNameAndTypeProductList")
  public void testListProductByCategoryNameAndCategoryType(){
    productService.listProductByCategoryNameAndCategoryType("갱년기 남", "생리활성");
  }

  @Test
  @DisplayName("다중 카테고리 이름으로 상품 조회 리스트 테스트")
  public void testListByProductCategoryNameList(){
      List<String> names = List.of("관절, 뼈", "피부");
      List<ProductDto> result = productService.listProductByCategoryNameList(names);
      log.info(result);
  }
  

}
