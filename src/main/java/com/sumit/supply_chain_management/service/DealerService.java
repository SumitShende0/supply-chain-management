package com.sumit.supply_chain_management.service;

import com.sumit.supply_chain_management.dto.DealerOrderDTO;
import com.sumit.supply_chain_management.model.Dealer;
import com.sumit.supply_chain_management.model.Order;
import com.sumit.supply_chain_management.repository.DealerRepository;
import com.sumit.supply_chain_management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealerService {

    @Autowired
    DealerRepository dealerRepo;

    @Autowired
    OrderRepository orderRepo;

    public Dealer registerDealer(Dealer dealer) {
           return dealerRepo.save(dealer);
    }

    public List<DealerOrderDTO> pendingOrder(int dealerId) {
        List<Order> orders = orderRepo.findByDealerDealerIdAndIsAcceptedIsNullOrderByDealerAssignmentDateAsc(dealerId);
        List<DealerOrderDTO> dtoList = new ArrayList<>();

        for(Order order: orders){
            DealerOrderDTO orderDTO = new DealerOrderDTO();
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setDeliveryDate(order.getDeliveryDate().toString());
            orderDTO.setCustomerName(order.getCustomer().getContactNumber());
            orderDTO.setProductName(order.getProduct().getProductName());
            orderDTO.setQuantity(order.getQuantityOrdered());
            orderDTO.setShippingAddress(order.getShippingAddress());
            orderDTO.setIsAccepted(order.getIsAccepted());
            orderDTO.setDealerAssignmentDate(order.getDealerAssignmentDate() != null
                    ? order.getDealerAssignmentDate().toString()
                    : null);
            orderDTO.setIsDispatched(order.getIsDispatched());

            dtoList.add(orderDTO);
        }

        return dtoList;

    }

    public List<DealerOrderDTO> trackedOrders(int dealerId) {
        List<Order> orders = orderRepo.findByDealerDealerIdAndIsAcceptedIsTrueOrderByDeliveryDateAsc(dealerId);
        List<DealerOrderDTO> dtoList = new ArrayList<>();

        for(Order order: orders){
            DealerOrderDTO orderDTO = new DealerOrderDTO();
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setDeliveryDate(order.getDeliveryDate().toString());
            orderDTO.setCustomerName(order.getCustomer().getContactNumber());
            orderDTO.setProductName(order.getProduct().getProductName());
            orderDTO.setQuantity(order.getQuantityOrdered());
            orderDTO.setShippingAddress(order.getShippingAddress());
            orderDTO.setIsAccepted(order.getIsAccepted());
            orderDTO.setDealerAssignmentDate(order.getDealerAssignmentDate() != null
                    ? order.getDealerAssignmentDate().toString()
                    : null);
            orderDTO.setIsDispatched(order.getIsDispatched());

            dtoList.add(orderDTO);
        }

        return dtoList;
    }

    public Order acceptOrder(int dealerId, int orderId) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order != null && order.getDealer() != null && order.getDealer().getDealerId() == dealerId) {
            order.setIsAccepted(true);
            order.setDealerAssignmentDate(java.time.LocalDate.now());
            return orderRepo.save(order);
        }
        return null;
    }

    public Order rejectOrder(int dealerId, int orderId) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order != null && order.getDealer().getDealerId() == dealerId) {
            order.setIsAccepted(false);
            order.setDealer(null);
            order.setDealerAssignmentDate(null);
            return orderRepo.save(order);
        }
        return null;
    }

    public Order dispatchOrder(int dealerId, int orderId, Order incomingOrder) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order != null && order.getDealer().getDealerId() == dealerId) {
            order.setIsDispatched(true);
            order.setDeliveryDate(incomingOrder.getDeliveryDate());
            order.setShippingAddress(incomingOrder.getShippingAddress());
            return orderRepo.save(order);
        }
        return null;
    }
}
