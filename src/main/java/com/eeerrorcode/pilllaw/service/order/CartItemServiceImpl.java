package com.eeerrorcode.pilllaw.service.order;

import com.eeerrorcode.pilllaw.dto.order.CartItemDto;
import com.eeerrorcode.pilllaw.entity.order.CartItem;
import com.eeerrorcode.pilllaw.entity.product.ProductPrice;
//import com.eeerrorcode.pilllaw.repository.product.ProductPriceRepository;
import com.eeerrorcode.pilllaw.repository.order.CartItemRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductPriceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public Long addCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = toEntity(cartItemDto);
    
        // ProductPrice 조회 및 가격 설정
        ProductPrice price = productPriceRepository
                .findByProductPno(cartItem.getProduct().getPno())
                .orElseThrow(() -> new RuntimeException("Product price not found for pno: " +
                        cartItem.getProduct().getPno()));
    
        log.info("Found sale price: {}", price.getSalePrice()); // 값 확인
        if (price.getSalePrice() != null) {
            cartItem.setPrice(price.getSalePrice());
        } else {
            throw new RuntimeException("Sale price is null for product with pno: " + cartItem.getProduct().getPno());
        }    
        // pno와 subday가 동일한 CartItem이 있는지 확인
        CartItem existingCartItem = cartItemRepository
                .findByCartAndProductAndSubday(cartItem.getCart(), cartItem.getProduct(), cartItem.getSubday());
    
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
            existingCartItem.setPrice(price.getSalePrice()); // 기존 아이템 가격 업데이트 추가
            cartItemRepository.save(existingCartItem);
            log.info("Updated existing cart item price: {}", existingCartItem.getPrice());
            return existingCartItem.getCino();
        }
    
        // CartItem이 존재하지 않으면 새로 저장
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        log.info("Saved cart item price: {}", savedCartItem.getPrice());
        return savedCartItem.getCino();
    }

    @Override
    public CartItemDto getCartItem(Long cino) {
        CartItem cartItem = cartItemRepository.findById(cino)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        return toDto(cartItem); // Entity -> DTO 변환 후 반환
    }

    @Override
    public int updateCartItem(CartItemDto cartItemDto) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemDto.getCino());

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();

            // 수량 변경 시
            if (cartItemDto.getQuantity() > 0) {
                cartItem.setQuantity(cartItemDto.getQuantity());
            }

            // 옵션(subday) 변경 시
            if (cartItemDto.getSubday() > 0) {
                cartItem.setSubday(cartItemDto.getSubday());
            }

            cartItemRepository.save(cartItem);
            return 1;
        } else {
            throw new RuntimeException("CartItem not found for cino: " + cartItemDto.getCino());
        }
    }

    @Override
    public int removeCartItem(Long cino) {
        cartItemRepository.deleteById(cino);
        return 1; // 성공적으로 삭제
    }
}
