package com.pizza.server.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.pizza.server.models.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll(Sort sort);
}
