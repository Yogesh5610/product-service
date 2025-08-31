package com.product.product.controller;

import com.product.product.dto.ProductDto;
import com.product.product.dto.ProductFilterDto;
import com.product.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/getProducts")
    public Page<ProductDto> getAllProducts(@RequestBody ProductFilterDto productFilterDto){
        return productService.getAllProducts(productFilterDto);
    }

    @PostMapping("/addAndUpdateProduct")
    public String addAndUpdateProduct(@RequestBody ProductDto dto) {
        return productService.addAndUpdateProduct(dto);
    }

    @GetMapping("/getProductById")
    public ProductDto getProductById(@RequestParam Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Long id) {
        return productService.deleteProduct(id);
    }


}
