package com.sumit.supply_chain_management.service;

import com.sumit.supply_chain_management.model.Product;
import com.sumit.supply_chain_management.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService  {
    @Autowired
    private ProductRepository productRepository;

    public Product findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    public Product findByProductId(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }
}

