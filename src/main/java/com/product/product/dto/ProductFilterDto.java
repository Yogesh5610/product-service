package com.product.product.dto;

import com.product.product.enums.IsActive;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProductFilterDto {

    private LocalDate fromDate;
    private LocalDate toDate;
    private String productName;
    private String category;
    private IsActive isActive;
    private Integer pageNumber;
    private Integer PageSize;
}
