package com.sumit.supply_chain_management.service;

import com.sumit.supply_chain_management.model.Customer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.repository.CustomerRepository;
import com.sumit.supply_chain_management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerSerevice {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private OrderRepository orderRepo;

    public Customer registerCustomer(Customer customer) {
        return customerRepo.save(customer);
    }


    public Order placeOrder(Order order) {
         return  orderRepo.save(order);
    }
}
