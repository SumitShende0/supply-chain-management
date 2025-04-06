package com.sumit.supply_chain_management.service;

import com.sumit.supply_chain_management.model.Dealer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private OrderRepository orderRepo;


    public List<Order> getPendingOrders() {
        return orderRepo.findByDealerAssignmentDateIsNullAndDealerIsNullOrderByOrderDateAsc();
    }

    public boolean assignDealerToOrder(int orderId, int dealerId) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order != null && order.getDealer() == null) {
            Dealer dealer = new Dealer();
            dealer.setDealerId(dealerId);
            order.setDealer(dealer);
            order.setDealerAssignmentDate(LocalDate.now());
            orderRepo.save(order);
            return true;
        }
        return false;
    }
}
