package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.dto.DealerOrderDTO;
import com.sumit.supply_chain_management.model.Dealer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/dealer")
public class    DealerController {

    @Autowired
    DealerService service;

    @PostMapping("/register")
    public ResponseEntity<Dealer> registerDealer(@RequestBody Dealer dealer){
        Dealer savedDealer = service.registerDealer(dealer);
        return new ResponseEntity<>(savedDealer, HttpStatus.CREATED);
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
