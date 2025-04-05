package com.sumit.supply_chain_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;
    private LocalDate orderDate;
    private String shippingAddress;
    private Integer quantityOrdered;
    private LocalDate dealerAssignmentDate;
    private Boolean isAccepted;
    private String deliveryDate;


    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "dealerId")
    private Dealer dealer;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
