package com.sumit.supply_chain_management.repository;

import com.sumit.supply_chain_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {


    boolean existsByUserEmail(String officialEmail);

    User findByUserEmail(String userEmail);

    User findByUsername(String username);
}
