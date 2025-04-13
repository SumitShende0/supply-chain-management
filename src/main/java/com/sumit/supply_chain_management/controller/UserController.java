package com.sumit.supply_chain_management.controller;

import com.sumit.supply_chain_management.model.User;
import com.sumit.supply_chain_management.service.JwtService;
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

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userRequest){
        try {
            // Check if user exists first
            User user = userService.findByEmail(userRequest.getUserEmail());
            if (user == null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Email not registered");
                return ResponseEntity.status(401).body(error);
            }

            // Try authenticating the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getUserEmail(), userRequest.getUserPassword())
            );

            if (authentication.isAuthenticated()) {
                // Generate JWT token
                String jwt = jwtService.generateToken(
                        user.getUserId(),
                        user.getUserEmail(),
                        user.getUserRole()
                );

                // Prepare response
                Map<String, Object> response = new HashMap<>();
                response.put("token", jwt);
                response.put("userId", user.getUserId());
                response.put("email", user.getUserEmail());
                response.put("role", user.getUserRole());

                return ResponseEntity.ok(response);
            } else {
                // Just in case
                Map<String, String> error = new HashMap<>();
                error.put("message", "Invalid credentials");
                return ResponseEntity.status(401).body(error);
            }

        } catch (Exception ex) {
            // Incorrect password or authentication failure
            Map<String, String> error = new HashMap<>();
            error.put("message", "Password does not match");
            return ResponseEntity.status(401).body(error);
        }
    }
}
