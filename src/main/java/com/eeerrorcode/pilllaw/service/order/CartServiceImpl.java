package com.eeerrorcode.pilllaw.service.order;

import com.eeerrorcode.pilllaw.dto.order.CartDto;
import com.eeerrorcode.pilllaw.entity.order.Cart;
import com.eeerrorcode.pilllaw.entity.order.CartItem;
import com.eeerrorcode.pilllaw.repository.order.CartRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  @Autowired
  private CartRepository cartRepository;

  @Override
  public Long addCart(CartDto cartDto) {
    Cart cart = toEntity(cartDto); // DTO -> Entity 변환
    cartRepository.save(cart);
    return cart.getCno();
  }

  @Override
  public List<CartItem> getItemsByMemberMno(Long mno) {
      // 회원의 mno를 사용해 장바구니를 찾습니다.
      Optional<Cart> cart = cartRepository.findByMemberMno(mno);
      if (cart.isEmpty()) {  // Optional이 비어있다면
          throw new RuntimeException("Cart not found for member with mno: " + mno);
      }
      // 해당 장바구니에 속한 모든 CartItem을 반환합니다.
      return cart.get().getCartItems();  // cart.get()으로 Cart 객체를 가져옴
  }


  @Override
  public Optional<CartDto> getCartByMember(Long mno) {
    Optional<Cart> cart = cartRepository.findByMemberMno(mno);
    return cart.map(this::toDto); // Entity -> DTO 변환 후 반환
  }

  @Override
  public int removeCart(Long cno) {
    cartRepository.deleteById(cno);
    return 1; // 성공적으로 삭제
  }
}
