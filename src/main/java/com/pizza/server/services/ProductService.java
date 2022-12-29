package com.pizza.server.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza.server.errors.ElementNotFoundException;
import com.pizza.server.models.Product;
import com.pizza.server.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> readProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public Product readProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    public Product updateProduct(Long id, Product product) {
        Product storedProduct = productRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
        if (storedProduct != null) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null;
    }

    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
        if (product != null) {
            productRepository.deleteById(id);
        }
        return product;
    }

}
