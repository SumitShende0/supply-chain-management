package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.model.Customer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.service.CustomerSerevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerSerevice service;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
        Customer savedCustomer = service.registerCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }


    @PostMapping("/order")
    public  ResponseEntity<Order> placeOrder(@RequestBody Order order){
        Order placedOrder = service.placeOrder(order);
        return  new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

}
