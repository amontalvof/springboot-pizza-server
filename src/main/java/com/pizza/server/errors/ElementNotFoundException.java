package com.pizza.server.errors;

public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(Long id) {
        super("The id '" + id + "' does not exist in our records.");
    }

}
