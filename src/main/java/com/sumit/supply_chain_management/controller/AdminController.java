package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService service;

    @GetMapping("/orders/pending")
    public ResponseEntity<?> pendingOrders() {
        List<Order> orders = service.getPendingOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
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

}
