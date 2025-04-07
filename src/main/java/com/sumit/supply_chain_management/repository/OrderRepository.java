package com.sumit.supply_chain_management.repository;

import com.sumit.supply_chain_management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
   public List<Order> findByDealerDealerIdAndIsAcceptedIsNullOrderByDealerAssignmentDateAsc(int dealerId);

   public List<Order> findByDealerDealerIdAndIsAcceptedIsTrueOrderByDeliveryDateAsc(int dealerId);

   @Query("SELECT o FROM Order o WHERE o.dealerAssignmentDate IS NULL AND (o.dealer IS NULL OR o.dealer.active = false) ORDER BY o.orderDate ASC")
   List<Order> findPendingOrders();
}
