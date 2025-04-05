package com.sumit.supply_chain_management.service;

import com.sumit.supply_chain_management.model.Dealer;
import com.sumit.supply_chain_management.repository.DealerRepository;
import org.springframework.stereotype.Service;

@Service
public class DealerService {
    DealerRepository repo;

    public Dealer registerDealer(Dealer dealer) {
           return repo.save(dealer);
    }
}
