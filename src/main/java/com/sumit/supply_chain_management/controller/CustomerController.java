package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.model.Customer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.model.Product;
import com.sumit.supply_chain_management.model.User;
import com.sumit.supply_chain_management.service.CustomerSerevice;
import com.sumit.supply_chain_management.service.JwtService;
import com.sumit.supply_chain_management.service.ProductService;
import com.sumit.supply_chain_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerSerevice service;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer){
        if (userService.existsByEmail(customer.getOfficialEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Email already registered"));
        }

        User user = new User();
        user.setUserEmail(customer.getOfficialEmail());
        user.setUserPassword(userService.encodePassword(customer.getPassword()));
        user.setUserRole("CUSTOMER");

        customer.setUser(user);

        Customer savedCustomer = service.registerCustomer(customer);
        User savedUser = savedCustomer.getUser();
        // Generate token after saving
        String jwt = jwtService.generateToken(
                savedUser.getUserId(),
                savedUser.getUserEmail(),
                savedUser.getUserRole()
        );

        // Return same response as login
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("userId", savedUser.getUserId());
        response.put("email", savedUser.getUserEmail());
        response.put("role", savedUser.getUserRole());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/order")
    public  ResponseEntity<?> placeOrder(@RequestBody Order order, Principal principal){
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Some error occurred"));
        }
        Customer customer = service.getCustomerByUserUserId(user.getUserId());
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Customer not found"));
        }
        Product product = productService.findByProductId(order.getProduct().getProductId());
        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Product not found"));
        }

        order.setCustomer(customer);
        order.setProduct(product);
        order.setIsDispatched(false);
        Order placedOrder = service.placeOrder(order);
        return  new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

    @GetMapping("product/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String query){
        List<Product> products = service.searchProduct(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Customer customer = service.getCustomerByUserUserId(user.getUserId());
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<Order> orders = service.getOrdersByCustomer(customer);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
