package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.dto.DealerOrderDTO;
import com.sumit.supply_chain_management.model.Dealer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.model.User;
import com.sumit.supply_chain_management.service.DealerService;
import com.sumit.supply_chain_management.service.JwtService;
import com.sumit.supply_chain_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dealer")
@CrossOrigin(origins = "*")
public class DealerController {

    @Autowired
    DealerService service;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerDealer(@RequestBody Dealer dealer){
        if (userService.existsByEmail(dealer.getOfficialEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Email already registered"));
        }

        User user = new User();
        user.setUserEmail(dealer.getOfficialEmail());
        user.setUserPassword(userService.encodePassword(dealer.getPassword()));
        user.setUserRole("DEALER");

        dealer.setUser(user);

        Dealer savedDealer = service.registerDealer(dealer);// save both dealer and user
        User savedUser = savedDealer.getUser();
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

    @GetMapping("/{dealerId}/pending-orders")
    public ResponseEntity<?> pendingOrders(@PathVariable int dealerId){
        List<DealerOrderDTO> pendingOrders = service.pendingOrder(dealerId);
        if (pendingOrders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else {
            return new ResponseEntity<>(pendingOrders, HttpStatus.OK);
        }
    }

    @GetMapping("/{dealerId}/track-orders")
    public ResponseEntity<?> trackedOrders(@PathVariable int dealerId){

        List<DealerOrderDTO> trackOrders = service.trackedOrders(dealerId);
        if (trackOrders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return new ResponseEntity<>(trackOrders, HttpStatus.OK);
        }
    }

    @PutMapping("/{dealerId}/pending-orders/{orderId}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable int dealerId, @PathVariable int orderId){
        Order order = service.acceptOrder(dealerId, orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    @PutMapping("/{dealerId}/pending-orders/{orderId}/reject")
    public ResponseEntity<?> rejectOrder(@PathVariable int dealerId, @PathVariable int orderId){
        Order order = service.rejectOrder(dealerId, orderId);
        if (order == null){
            return ResponseEntity.notFound().build();
        }else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    @PutMapping("/{dealerId}/track-orders/{orderId}/dispatch")
    public ResponseEntity<?> dispatchOrder(@PathVariable int dealerId, @PathVariable int orderId, @RequestBody Order incomingOrder){
        Order order = service.dispatchOrder(dealerId, orderId, incomingOrder);
        if (order == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }
}
