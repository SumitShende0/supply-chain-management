package com.sumit.supply_chain_management.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealerOrderDTO {
    private Integer orderId;
    private String deliveryDate;
    private String customerName;
    private String productName;
    private Integer quantity;
    private String shippingAddress;
    private Boolean isAccepted;
    private String dealerAssignmentDate;
    private Boolean isDispatched;
}
