package com.pizza.server.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pizza.server.models.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
