package com.product.product.service;

import com.product.product.dto.ProductDto;
import com.product.product.dto.ProductFilterDto;
import com.product.product.entity.Products;
import com.product.product.repo.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String addAndUpdateProduct(ProductDto dto) {
        Products product;

        if (dto.getId() != null && dto.getId() > 0) {
            product = productRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        } else {
            product = new Products();
        }

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());
        product.setIsActive(dto.getIsActive());

        productRepository.save(product);

        if(dto.getId() != null && dto.getId() > 0){
            return "Product updated successfully!";
        } else {
            return "Product saved successfully!";
        }
    }

    public Page<ProductDto> getAllProducts(ProductFilterDto filter) {
        Specification<Products> spec = getProductsByFilter(filter);

        int pageNumber = (filter.getPageNumber() == null || filter.getPageNumber() < 0) ? 0 : filter.getPageNumber();
        int pageSize = (filter.getPageSize() == null || filter.getPageSize() <= 0) ? 10 : filter.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Page<Products> productPage = productRepository.findAll(spec, pageable);

        return productPage.map(product -> convertToDto(product));
    }

    public ProductDto getProductById(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDto(product);
    }

    public String deleteProduct(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
        return "Product deleted successfully!";
    }

    private ProductDto convertToDto(Products product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategory(product.getCategory());
        dto.setIsActive(product.getIsActive());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }

    private static Specification<Products> getProductsByFilter(ProductFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getProductName() != null && !filter.getProductName().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getProductName().toLowerCase() + "%"));
            }

            if (filter.getCategory() != null && !filter.getCategory().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), filter.getCategory()));
            }

            if (filter.getIsActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), filter.getIsActive()));
            }

            if (filter.getFromDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filter.getFromDate()));
            }

            if (filter.getToDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filter.getToDate()));
            }

            // Add sorting by id descending
            query.orderBy(criteriaBuilder.desc(root.get("id")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
