package com.pizza.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza.server.models.ExtraOption;
import com.pizza.server.models.Product;
import com.pizza.server.repositories.ExtraOptionRepository;
import com.pizza.server.repositories.ProductRepository;

@Service
public class ExtraOptionService {
    @Autowired
    private ExtraOptionRepository extraOptionRepository;
    @Autowired
    private ProductRepository productRepository;

    public ExtraOption createExtraOption(Long productId, ExtraOption extraOption) {
        Product product = productRepository.findById(productId).get();
        extraOption.setProduct(product);
        return extraOptionRepository.save(extraOption);
    }

    public List<ExtraOption> findByProductId(Long productId) {
        return extraOptionRepository.findByProductId(productId);
    }
}
