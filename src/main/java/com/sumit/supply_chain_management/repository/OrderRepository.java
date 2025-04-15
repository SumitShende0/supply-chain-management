package com.sumit.supply_chain_management.repository;

import com.sumit.supply_chain_management.model.Customer;
import com.sumit.supply_chain_management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
   public List<Order> findByDealerDealerIdAndIsAcceptedIsNullOrderByDealerAssignmentDateAsc(int dealerId);

   public List<Order> findByDealerDealerIdAndIsAcceptedIsTrueOrderByDeliveryDateAsc(int dealerId);

   List<Order> findByIsDispatchedFalse();

    List<Order> findByCustomer(Customer customer);
}
