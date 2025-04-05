package com.sumit.supply_chain_management.repository;

import com.sumit.supply_chain_management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
