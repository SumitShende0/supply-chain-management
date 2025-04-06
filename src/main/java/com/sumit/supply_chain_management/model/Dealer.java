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
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer dealerId;
    private String contactPerson;
    private String contactNumber;
    private String orgName;
    private String warehouseAddress;
    private String officeAddress;
    private String officialEmail;

    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}
