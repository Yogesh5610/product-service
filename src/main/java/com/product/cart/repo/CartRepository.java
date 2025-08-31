package com.product.cart.repo;

import com.product.cart.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Carts,Long> {

    List<Carts> findByUserId(Long userId);
}
