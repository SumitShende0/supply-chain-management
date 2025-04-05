package com.sumit.supply_chain_management.repository;

import com.sumit.supply_chain_management.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer, Integer> {
}