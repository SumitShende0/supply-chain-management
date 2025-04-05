package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.model.Dealer;
import com.sumit.supply_chain_management.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/dealer")
public class DealerController {

    @Autowired
    DealerService service;

    @PostMapping("/register")
    public ResponseEntity<Dealer> registerDealer(@RequestBody Dealer dealer){
        Dealer savedDealer = service.registerDealer(dealer);
        return new ResponseEntity<>(savedDealer, HttpStatus.CREATED);
    }
}
