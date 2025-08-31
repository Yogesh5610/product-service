package com.product.cart.controller;

import com.product.cart.dto.CartRequestDto;
import com.product.cart.dto.CartResponseDto;
import com.product.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/getCart")
    public List<CartResponseDto> getCart() {
        return cartService.getCartItems();
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestBody CartRequestDto cartRequestDto) {
        return cartService.addToCart(cartRequestDto);
    }

    @DeleteMapping("/deleteFromCart")
    public String deleteFromCart(@RequestParam Long cartId) {
        return cartService.deleteFromCart(cartId);
    }

    @PatchMapping("/updateCart")
    public String updateCart(@RequestBody CartRequestDto cartRequestDto) {
        return cartService.updateCart(cartRequestDto);
    }


}
