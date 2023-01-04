package com.pizza.server.utils;

import java.util.Optional;

import com.pizza.server.errors.ElementNotFoundException;

public class Helpers {
    public static <T> T unwrapEntity(Optional<T> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new ElementNotFoundException(id);
        }
    }
}
