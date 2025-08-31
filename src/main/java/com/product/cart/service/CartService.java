package com.product.cart.service;

import com.netflix.discovery.converters.Auto;
import com.product.cart.dto.CartRequestDto;
import com.product.cart.dto.CartResponseDto;
import com.product.cart.entity.Carts;
import com.product.cart.repo.CartRepository;
import com.product.jwtToken.JwtToken;
import com.product.product.entity.Products;
import com.product.product.repo.ProductRepository;
import com.product.user.entity.User;
import com.product.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtToken jwtToken;

    public String addToCart(CartRequestDto cartRequestDto){
        Long userId = jwtToken.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Products products = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Carts carts= new Carts();
        carts.setUser(user);
        carts.setProducts(products);
        carts.setQuantity(cartRequestDto.getQuantity());
        cartRepository.save(carts);
        return "Product added to cart successfully!";
    }

    public String deleteFromCart(Long cartId) {
        Carts carts = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cartRepository.delete(carts);
        return "Product removed from cart successfully!";
    }

    public String updateCart(CartRequestDto cartRequestDto) {
        Carts carts = cartRepository.findById(cartRequestDto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        carts.setQuantity(cartRequestDto.getQuantity());
        cartRepository.save(carts);
        return "Cart updated successfully!";
    }

    public List<CartResponseDto> getCartItems() {
        Long userId = jwtToken.getUserId();
        List<Carts> cartsList = cartRepository.findByUserId(userId);
        return cartsList.stream().map(cart -> {
            CartResponseDto responseDto = new CartResponseDto();
            responseDto.setCartId(cart.getId());
            responseDto.setProductName(cart.getProducts().getName());
            responseDto.setQuantity(cart.getQuantity());
            responseDto.setPrice(cart.getProducts().getPrice());
            double totalPrice = cart.getQuantity() * cart.getProducts().getPrice();
            BigDecimal roundedPrice = BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP);
            responseDto.setTotalPrice(roundedPrice.doubleValue());
            return responseDto;
        }).toList();
    }


}
