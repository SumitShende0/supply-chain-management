package com.sumit.supply_chain_management.repository;

import com.sumit.supply_chain_management.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Integer> {
    Dealer findByUserUserId(Integer userId);
}