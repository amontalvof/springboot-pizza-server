package com.pizza.server.services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Sort;

import com.pizza.server.models.Order;
import com.pizza.server.repositories.OrderRepository;
import com.pizza.server.utils.Helpers;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> readOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).forEach(orders::add);
        return orders;
    }

    public Order readOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return Helpers.unwrapEntity(order, id);
    }

    public Order updateOrder(Long id, Order order) {
        Optional<Order> storedOrder = orderRepository.findById(id);
        Helpers.unwrapEntity(storedOrder, id);
        order.setId(id);
        return orderRepository.save(order);
    }

}
