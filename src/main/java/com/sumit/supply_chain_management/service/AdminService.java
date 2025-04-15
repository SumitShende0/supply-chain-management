package com.sumit.supply_chain_management.service;

import com.sumit.supply_chain_management.model.Dealer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.model.Product;
import com.sumit.supply_chain_management.repository.DealerRepository;
import com.sumit.supply_chain_management.repository.OrderRepository;
import com.sumit.supply_chain_management.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private DealerRepository dealerRepo;


    public List<Order> getPendingOrders() {
        return orderRepo.findByIsDispatchedFalse();
    }

    public boolean assignDealerToOrder(int orderId, int dealerId) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order != null && order.getDealer() == null) {
            Dealer dealer = new Dealer();
            dealer.setDealerId(dealerId);
            order.setDealer(dealer);
            order.setDealerAssignmentDate(LocalDate.now());
            order.setIsAccepted(null);
            orderRepo.save(order);
            return true;
        }
        return false;
    }

    public Product addProduct(Product product) {
        // Check if the product name is already taken
        if (productRepo.existsByProductName(product.getProductName())) {
            throw new IllegalArgumentException("Product name already exists");
        }
        // Save the product to the database
        return productRepo.save(product);
    }

    public List<Dealer> getAllDealers() {
        return dealerRepo.findAll();
    }
}
