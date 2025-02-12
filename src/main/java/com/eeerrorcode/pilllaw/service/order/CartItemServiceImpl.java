package com.eeerrorcode.pilllaw.service.order;

import com.eeerrorcode.pilllaw.dto.order.CartItemDto;
import com.eeerrorcode.pilllaw.entity.order.CartItem;
import com.eeerrorcode.pilllaw.entity.product.ProductPrice;
//import com.eeerrorcode.pilllaw.repository.product.ProductPriceRepository;
import com.eeerrorcode.pilllaw.repository.order.CartItemRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    //@Autowired
    //private ProductPriceRepository productPriceRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Long addCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = toEntity(cartItemDto); // DTO -> Entity 변환
        // ProductPrice 조회 및 가격 설정
        //ProductPrice productPrice = productPriceRepository
        //        .findTopByProductPnoOrderByRegdateDesc(cartItem.getProduct().getPno());
        //if (productPrice != null) {
        //    cartItem.setPrice(productPrice.getPrice());
        //} else {
        //    throw new RuntimeException("Product price not found for pno: " + cartItem.getProduct().getPno());
        //}

        // CartItem 저장
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
        CartItem cartItem = toEntity(cartItemDto);
        cartItemRepository.save(cartItem);
        return 1; // 성공적으로 업데이트
    }

    @Override
    public int removeCartItem(Long cino) {
        cartItemRepository.deleteById(cino);
        return 1; // 성공적으로 삭제
    }
}
