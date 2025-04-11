package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.model.User;
import com.sumit.supply_chain_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUserEmail(), userRequest.getUserPassword()));
        if (authentication.isAuthenticated()) {
            User user = userService.findByEmail(userRequest.getUserEmail());

            String jwt = jwtService.generateToken(
                    user.getUserId(),
                    user.getUserEmail(),
                    user.getUserRole()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("userId", user.getUserId());
            response.put("email", user.getUserEmail());
            response.put("role", user.getUserRole());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Login failed");
        }
    }
}
