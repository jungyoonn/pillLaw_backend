package com.eeerrorcode.pilllaw.repository.order;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.order.Cart;
import com.eeerrorcode.pilllaw.entity.order.CartItem;
import com.eeerrorcode.pilllaw.entity.product.Product;

import lombok.extern.log4j.Log4j2;


@SpringBootTest
@Log4j2
@Transactional
public class CartRepositoryTests {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    @Rollback(false)
    public void testInsertCartWithItems() { 
        
        Cart savedCart = Cart.builder().member(Member.builder().mno(11L).build()).build();
        cartRepository.save(savedCart);

        CartItem ci1 = CartItem.builder().cart(savedCart).product(Product.builder().pno(16L).build()).subday(60)
                .quantity(2).build();
        CartItem ci2 = CartItem.builder().cart(savedCart).product(Product.builder().pno(17L).build()).subday(60)
                .quantity(1).build();
        CartItem ci3 = CartItem.builder().cart(savedCart).product(Product.builder().pno(18L).build()).subday(30)
                .quantity(2).build();
        CartItem ci4 = CartItem.builder().cart(savedCart).product(Product.builder().pno(19L).build()).subday(30)
                .quantity(3).build();

        savedCart.getCartItems().add(ci1);
        savedCart.getCartItems().add(ci2);
        savedCart.getCartItems().add(ci3);
        savedCart.getCartItems().add(ci4);

        cartItemRepository.saveAll(List.of(ci1, ci2, ci3, ci4));

        cartRepository.save(savedCart);

        List<CartItem> cartItems = savedCart.getCartItems();
        log.info(cartItems);
    }

    @Test
    public void testListCartItems() {
        List<CartItem> cartItems = cartItemRepository.findByCartCno(2L);
        log.info(cartItems);
    }

    @Test
    @Rollback(false)
    public void testDeleteAll() {
        cartItemRepository.deleteAllByCartCno(2L);
    }

    @Test
    @Rollback(false)
    public void testDeleteOne() {
        cartItemRepository.deleteById(17L);
    }

    @Test
    @Rollback(false) 
    public void testUpdate() {
        CartItem cartItem = cartItemRepository.findById(16L).orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItem.setQuantity(2L);
        cartItemRepository.save(cartItem);
    }
}


