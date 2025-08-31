package com.product.cart.dto;

import lombok.Getter;

@Getter
public class CartRequestDto {

    private Long cartId;
    private Long productId;
    private Integer quantity;

}
