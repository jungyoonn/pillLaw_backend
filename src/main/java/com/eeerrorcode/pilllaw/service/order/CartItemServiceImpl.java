package com.eeerrorcode.pilllaw.service.order;

import com.eeerrorcode.pilllaw.dto.order.CartItemDto;
import com.eeerrorcode.pilllaw.entity.order.CartItem;
import com.eeerrorcode.pilllaw.entity.product.ProductPrice;
//import com.eeerrorcode.pilllaw.repository.product.ProductPriceRepository;
import com.eeerrorcode.pilllaw.repository.order.CartItemRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    // productpricerepository만들어지면 주석풀기
    // @Autowired
    // private ProductPriceRepository productPriceRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Long addCartItem(CartItemDto cartItemDto) {
        // DTO -> Entity 변환
        CartItem cartItem = toEntity(cartItemDto);
    
        // // ProductPrice 조회 및 가격 설정
        // ProductPrice productPrice = productPriceRepository
        //         .findTopByProductPnoOrderByRegdateDesc(cartItem.getProduct().getPno());
        // if (productPrice != null) {
        //     cartItem.setPrice(productPrice.getPrice());
        // } else {
        //     throw new RuntimeException("Product price not found for pno: " + cartItem.getProduct().getPno());
        // }
    
        // pno와 subday가 동일한 CartItem이 있는지 확인
        CartItem existingCartItem = cartItemRepository
                .findByCartAndProductAndSubday(cartItem.getCart(), cartItem.getProduct(), cartItem.getSubday());
    
        if (existingCartItem != null) {
            // 이미 존재하는 경우 수량을 증가시킴
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
            cartItemRepository.save(existingCartItem); // 업데이트된 CartItem 저장
            return existingCartItem.getCino();
        }
    
        // CartItem이 존재하지 않으면 새로 저장
        cartItemRepository.save(cartItem);
        return cartItem.getCino();
    }

    @Override
    public CartItemDto getCartItem(Long cino) {
        CartItem cartItem = cartItemRepository.findById(cino)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        return toDto(cartItem); // Entity -> DTO 변환 후 반환
    }

    @Override
    public int updateCartItem(CartItemDto cartItemDto) {
        // cino로 CartItem을 찾습니다.
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemDto.getCino());
        
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            
            // CartItem을 수정합니다. (quantity만 수정하는 예시)
            cartItem.setQuantity(cartItemDto.getQuantity());
            // 필요에 따라 다른 필드들도 수정 가능
            
            // 수정된 CartItem을 저장합니다.
            cartItemRepository.save(cartItem);
            return 1; // 성공적으로 업데이트
        } else {
            // 해당 cino에 맞는 CartItem이 없으면 예외를 던지거나 0을 반환할 수 있습니다.
            throw new RuntimeException("CartItem not found for cino: " + cartItemDto.getCino());
        }
    }

    @Override
    public int removeCartItem(Long cino) {
        cartItemRepository.deleteById(cino);
        return 1; // 성공적으로 삭제
    }
}
