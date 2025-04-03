package com.sumit.supply_chain_management.repository;

import com.sumit.supply_chain_management.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
