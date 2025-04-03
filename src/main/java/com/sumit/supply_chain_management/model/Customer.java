package com.sumit.supply_chain_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;
    private String orgName;
    private String contactPerson;
    private String contactNumber;
    private String officialEmail;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders =  new ArrayList<>();
}
