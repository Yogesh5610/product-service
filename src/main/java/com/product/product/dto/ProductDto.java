package com.product.product.dto;

import com.product.product.enums.IsActive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {

    private Long id;

    private String name;

    private IsActive isActive;

    private String description;

    private Double price;

    private Integer stock;

    private String category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
