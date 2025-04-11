package com.sumit.supply_chain_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Transient
    private String password;

    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"userPassword"})
    private User user;
}
