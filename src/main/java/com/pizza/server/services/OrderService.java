package com.pizza.server.services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza.server.models.Order;
import com.pizza.server.models.Product;
import com.pizza.server.repositories.OrderRepository;
import com.pizza.server.repositories.ProductRepository;
import com.pizza.server.utils.Helpers;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> readOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    public Order readOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return Helpers.unwrapEntity(order, id);
    }

    public Order addProductToOrder(Long orderId, Long productId) {
        Order order = readOrder(orderId);
        Optional<Product> product = productRepository.findById(productId);
        Product unwrappedProduct = Helpers.unwrapEntity(product, productId);
        order.getProducts().add(unwrappedProduct);
        return orderRepository.save(order);
    }

}
