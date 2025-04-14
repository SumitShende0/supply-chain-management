package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.model.Dealer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.model.Product;
import com.sumit.supply_chain_management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService service;

    @GetMapping("/orders/pending")
    public ResponseEntity<?> pendingOrders() {
        List<Order> orders = service.getPendingOrders();
        if (orders.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No pending orders found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @PutMapping("/orders/{orderId}/assign/{dealerId}")
    public ResponseEntity<?> assignedOrders(@PathVariable int orderId, @PathVariable int dealerId) {
        boolean updated = service.assignDealerToOrder(orderId, dealerId);

        if (updated) {
            return ResponseEntity.ok("Dealer assigned to order successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order or Dealer not found.");
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        Product savedProduct = service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/dealers")
    public ResponseEntity<?> getAllDealers() {
        List<Dealer> dealers = service.getAllDealers();
        if (dealers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<Map<String, Object>> result = dealers.stream().map(dealer -> {
                Map<String, Object> map = new HashMap<>();
                map.put("dealerId", dealer.getDealerId());
                map.put("name", dealer.getContactPerson());
                return map;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(result);
        }
    }

}
