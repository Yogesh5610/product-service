package com.product.order.service;

import com.product.cart.entity.Carts;
import com.product.cart.repo.CartRepository;
import com.product.order.entity.Order;
import com.product.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;


    public String placeOrder(List<Long> cartIds,String address) {
        List<Carts> cartItems = cartRepository.findAllById(cartIds);

        if (cartItems.isEmpty()) {
            return "No cart items found for given IDs";
        }

        // Get current max orderId or 0 if none
        Long maxOrderId = orderRepository.findMaxOrderId();

        Long newOrderId = maxOrderId + 1;

        List<Order> ordersToSave = new ArrayList<>();

        for (Carts cart : cartItems) {
            Order order = new Order();
            order.setOrderId(newOrderId);
            order.setProductName(cart.getProducts().getName());
            order.setQuantity(cart.getQuantity());
            order.setPrice(cart.getProducts().getPrice());
            order.setTotalAmount(cart.getQuantity() * cart.getProducts().getPrice());
            order.setUserId(cart.getUser().getId());
            order.setProductId(cart.getProducts().getId());

            // Set other details accordingly
            order.setCustomerName(cart.getUser().getUsername());
            order.setAddress(address);

            ordersToSave.add(order);
        }

        orderRepository.saveAll(ordersToSave);

        return "Order placed successfully with orderId: " + newOrderId;
    }
}
