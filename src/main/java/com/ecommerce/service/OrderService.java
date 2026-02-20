package com.ecommerce.service;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Order operations.
 */
public interface OrderService {

    OrderDTO createOrder(OrderRequest request);

    OrderDTO getOrderById(UUID id);

    OrderDTO getOrderByOrderNumber(String orderNumber);

    Page<OrderDTO> getUserOrders(UUID userId, Pageable pageable);

    Page<OrderDTO> getAllOrders(Pageable pageable);

    Page<OrderDTO> getOrdersByStatus(OrderStatus status, Pageable pageable);

    OrderDTO updateOrderStatus(UUID id, OrderStatus status, String note);

    void cancelOrder(UUID id, String reason);

    List<OrderDTO> getPendingShipmentOrders();
}
