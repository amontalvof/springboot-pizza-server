package com.pizza.server.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pizza.server.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
