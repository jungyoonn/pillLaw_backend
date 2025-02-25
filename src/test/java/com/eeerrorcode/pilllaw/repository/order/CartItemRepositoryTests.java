package com.eeerrorcode.pilllaw.repository.order;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.order.CartItemDto;
import com.eeerrorcode.pilllaw.entity.order.Cart;
import com.eeerrorcode.pilllaw.entity.order.CartItem;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductPrice;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductPriceRepository;
import com.eeerrorcode.pilllaw.service.order.CartItemService;
import com.eeerrorcode.pilllaw.service.order.CartService;

import lombok.experimental.PackagePrivate;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class CartItemRepositoryTests {
  @Autowired
  private CartItemRepository cartItemRepository;
  @Autowired
  private ProductPriceRepository productPriceRepository;

  @Autowired
  private CartItemService cartService;

  @Test
  @Rollback(false)
  public void testInsert() {
    
  }

  @Test
  @Transactional
  public void testAddCartItem_withExistingSalePrice() {
      // given
      // cno가 3인 Cart 객체 준비
      Cart cart = Cart.builder().cno(3L).build();
      
      // pno가 16인 Product 객체 준비
      Product product = Product.builder().pno(16L).build();
      
      // CartItemDto 생성 (Cart와 Product를 포함하여 수량과 subday 설정)
      CartItemDto cartItemDto = new CartItemDto();
      cartItemDto.setCno(3L);  // cno 3
      cartItemDto.setPno(16L);  // pno 16
      cartItemDto.setSubday(60);  // 예시 subday
      cartItemDto.setQuantity(2);  // 예시 quantity
      
      // ProductPrice가 존재하는 상태로 설정
      ProductPrice productPrice = new ProductPrice();
      productPriceRepository.save(productPrice);  // ProductPrice 저장
      
      // when
      // addCartItem 호출하여 CartItem 저장
      Long cartItemCino = cartService.addCartItem(cartItemDto);  // cartItem 저장
      
      // then
      // 저장된 CartItem을 찾아 price가 SalePrice로 설정되었는지 확인
      CartItem savedCartItem = cartItemRepository.findById(cartItemCino)
              .orElseThrow(() -> new RuntimeException("CartItem not found"));
      
      // price가 null이 아니고, salePrice가 올바르게 저장되었는지 확인
      assertNotNull(savedCartItem.getPrice());  // price가 null이 아님
      assertEquals(500L, savedCartItem.getPrice());  // salePrice 값 500으로 저장된 것 확인
      
      log.info("Saved cart item price: {}", savedCartItem.getPrice());
  }
  
}


