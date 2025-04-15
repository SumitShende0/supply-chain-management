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

import java.security.Principal;
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

    @GetMapping("/pending-orders")
    public ResponseEntity<?> pendingOrders(Principal principal){
        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        Dealer dealer = service.getDealerByUserId(user.getUserId());

        List<DealerOrderDTO> pendingOrders = service.pendingOrder(dealer.getDealerId());
        if (pendingOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No pending orders found"));
        }else {
            return new ResponseEntity<>(pendingOrders, HttpStatus.OK);
        }
    }

    @GetMapping("/track-orders")
    public ResponseEntity<?> trackedOrders(Principal principal){
        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        Dealer dealer = service.getDealerByUserId(user.getUserId());

        List<DealerOrderDTO> trackOrders = service.trackedOrders(dealer.getDealerId());
        if (trackOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No pending orders found"));
        }
        else {
            return new ResponseEntity<>(trackOrders, HttpStatus.OK);
        }
    }

    @PutMapping("/pending-orders/{orderId}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable int orderId, Principal principal){
        String email= principal.getName();
        User user = userService.getUserByEmail(email);

        Dealer dealer = service.getDealerByUserId(user.getUserId());
        int dealerId = dealer.getDealerId();
        Order order = service.acceptOrder(dealerId, orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Order not found"));
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    @PutMapping("/pending-orders/{orderId}/reject")
    public ResponseEntity<?> rejectOrder(@PathVariable int orderId, Principal principal){
        String email= principal.getName();
        User user = userService.getUserByEmail(email);

        Dealer dealer = service.getDealerByUserId(user.getUserId());
        int dealerId = dealer.getDealerId();
        Order order = service.rejectOrder(dealerId, orderId);
        if (order == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Order not found"));
        }else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    @PutMapping("/track-orders/{orderId}/dispatch")
    public ResponseEntity<?> dispatchOrder(Principal principal,@PathVariable int orderId, @RequestBody Order incomingOrder){
        String email= principal.getName();
        User user = userService.getUserByEmail(email);

        Dealer dealer = service.getDealerByUserId(user.getUserId());
        int dealerId = dealer.getDealerId();
        Order order = service.dispatchOrder(dealerId, orderId, incomingOrder);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Order not found"));
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }
}
