package com.sumit.supply_chain_management.service;

import com.sumit.supply_chain_management.model.Customer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.model.Product;
import com.sumit.supply_chain_management.repository.CustomerRepository;
import com.sumit.supply_chain_management.repository.OrderRepository;
import com.sumit.supply_chain_management.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerSerevice {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ProductRepository productRepo;

    public Customer registerCustomer(Customer customer) {
        return customerRepo.save(customer);
    }


    public Order placeOrder(Order order) {

         order.setOrderDate(LocalDate.now());
         return  orderRepo.save(order);
    }

    public List<Product> searchProduct(String query) {
        return productRepo.findByProductNameContainingIgnoreCase(query);
    }

    public Customer getCustomerByUserUserId(Integer userId) {
        return customerRepo.findByUserUserId(userId);
    }
}
