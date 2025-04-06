package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.dto.DealerOrderDTO;
import com.sumit.supply_chain_management.model.Dealer;
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
}
