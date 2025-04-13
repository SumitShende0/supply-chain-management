package com.sumit.supply_chain_management.repository;

import com.sumit.supply_chain_management.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByProductName(String productName);

    List<Product> findByProductNameContainingIgnoreCase(String query);

    Product findByProductName(String productName);
}
