package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.model.Customer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.model.Product;
import com.sumit.supply_chain_management.model.User;
import com.sumit.supply_chain_management.service.CustomerSerevice;
import com.sumit.supply_chain_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerSerevice service;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
        if (userService.existsByEmail(customer.getOfficialEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        User user = new User();
        user.setUserEmail(customer.getOfficialEmail());
        user.setUserPassword(customer.getPassword());
        user.setUserRole("CUSTOMER");

        user.setCustomer(customer);
        customer.setUser(user);

        Customer savedCustomer = service.registerCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }


    @PostMapping("/order")
    public  ResponseEntity<Order> placeOrder(@RequestBody Order order){
        Order placedOrder = service.placeOrder(order);
        return  new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

    @GetMapping("product/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String query){
        List<Product> products = service.searchProduct(query);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

}
