package com.pizza.server.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pizza.server.models.ExtraOption;

public interface ExtraOptionRepository extends CrudRepository<ExtraOption, Long> {
    List<ExtraOption> findByProductId(Long productId);
}
