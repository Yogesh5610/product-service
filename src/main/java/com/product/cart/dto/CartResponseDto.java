package com.product.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDto {

    private Long cartId;
    private String productName;
    private String category;
    private Double price;
    private Integer quantity;
    private Double totalPrice;

}
