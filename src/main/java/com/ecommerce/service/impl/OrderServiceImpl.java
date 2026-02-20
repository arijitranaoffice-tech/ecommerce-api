package com.ecommerce.service.impl;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.dto.OrderItemDTO;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.repository.*;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of OrderService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));

        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .user(user)
                .status(OrderStatus.PENDING)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .customerNote(request.getCustomerNote())
                .build();

        // Set shipping address
        if (request.getShippingAddressId() != null) {
            Address shippingAddress = addressRepository.findById(request.getShippingAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address", request.getShippingAddressId()));
            order.setShippingAddress(shippingAddress);
        }

        // Set billing address
        if (request.getBillingAddressId() != null) {
            Address billingAddress = addressRepository.findById(request.getBillingAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address", request.getBillingAddressId()));
            order.setBillingAddress(billingAddress);
        }

        // Add order items
        for (OrderItemDTO itemDTO : request.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemDTO.getProductId()));

            // Check stock availability
            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPrice(product.getPrice())
                    .taxAmount(product.getPrice().multiply(java.math.BigDecimal.valueOf(0.18)))
                    .build();

            order.addOrderItem(orderItem);

            // Update product stock
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            if (product.getStockQuantity() == 0) {
                product.setStatus(ProductStatus.OUT_OF_STOCK);
            }
            productRepository.save(product);
        }

        // Calculate totals
        order.setTaxAmount(order.getItems().stream()
                .map(OrderItem::getTaxAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
        order.calculateTotals();

        // Add status history
        OrderStatusHistory statusHistory = OrderStatusHistory.builder()
                .order(order)
                .status(OrderStatus.PENDING)
                .statusChangedAt(LocalDateTime.now())
                .note("Order created")
                .build();
        order.getStatusHistory().add(statusHistory);

        Order savedOrder = orderRepository.save(order);

        // Convert cart to converted status if exists
        cartRepository.findActiveCartByUserId(user.getId()).ifPresent(cart -> {
            cart.setStatus(CartStatus.CONVERTED);
            cartRepository.save(cart);
        });

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        return orderMapper.toDTO(order);
    }

    @Override
    public OrderDTO getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderNumber));
        return orderMapper.toDTO(order);
    }

    @Override
    public Page<OrderDTO> getUserOrders(UUID userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return orderRepository.findByUser(user, pageable).map(orderMapper::toDTO);
    }

    @Override
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toDTO);
    }

    @Override
    public Page<OrderDTO> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable).map(orderMapper::toDTO);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(UUID id, OrderStatus status, String note) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        OrderStatus oldStatus = order.getStatus();
        order.setStatus(status);

        // Update timestamps based on status
        if (status == OrderStatus.SHIPPED) {
            order.setShippedAt(LocalDateTime.now());
        } else if (status == OrderStatus.DELIVERED) {
            order.setDeliveredAt(LocalDateTime.now());
            order.setPaymentStatus(PaymentStatus.CAPTURED);
        }

        // Add status history
        OrderStatusHistory statusHistory = OrderStatusHistory.builder()
                .order(order)
                .status(status)
                .statusChangedAt(LocalDateTime.now())
                .note(note)
                .build();
        order.getStatusHistory().add(statusHistory);

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    @Override
    @Transactional
    public void cancelOrder(UUID id, String reason) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel order that has been shipped");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        order.setCancellationReason(reason);

        // Restore product stock
        order.getItems().forEach(item -> {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
                product.setStatus(ProductStatus.ACTIVE);
            }
            productRepository.save(product);
        });

        // Add status history
        OrderStatusHistory statusHistory = OrderStatusHistory.builder()
                .order(order)
                .status(OrderStatus.CANCELLED)
                .statusChangedAt(LocalDateTime.now())
                .note(reason)
                .build();
        order.getStatusHistory().add(statusHistory);

        orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> getPendingShipmentOrders() {
        return orderRepository.findPendingShipmentOrders(OrderStatus.CONFIRMED).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
