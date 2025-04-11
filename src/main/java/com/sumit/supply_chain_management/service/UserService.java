package com.sumit.supply_chain_management.service;

import com.sumit.supply_chain_management.model.User;
import com.sumit.supply_chain_management.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public boolean existsByEmail(String officialEmail) {
        return userRepo.existsByUserEmail(officialEmail);
    }


    public User saveUser(User user) {
        user.setUserPassword(encoder.encode(user.getUserPassword()));
        return userRepo.save(user) ;

    }
}
