package com.product.order.repository;

import com.product.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COALESCE(MAX(o.orderId), 0) FROM Order o")
    Long findMaxOrderId();
}
