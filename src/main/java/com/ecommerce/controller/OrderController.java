package com.ecommerce.controller;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for order endpoints.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Order created successfully", orderService.createOrder(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(id)));
    }

    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderByOrderNumber(@PathVariable String orderNumber) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderByOrderNumber(orderNumber)));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<Page<OrderDTO>>> getMyOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {
        // In a real application, you would get the user ID from the token
        return ResponseEntity.ok(ApiResponse.success(orderService.getAllOrders(pageable)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<OrderDTO>>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getAllOrders(pageable)));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<OrderDTO>>> getOrdersByStatus(
            @PathVariable OrderStatus status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrdersByStatus(status, pageable)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(
            @PathVariable UUID id,
            @RequestParam OrderStatus status,
            @RequestParam(required = false) String note
    ) {
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully", 
                orderService.updateOrderStatus(id, status, note)));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable UUID id,
            @RequestParam String reason
    ) {
        orderService.cancelOrder(id, reason);
        return ResponseEntity.ok(ApiResponse.success("Order cancelled successfully", null));
    }

    @GetMapping("/pending-shipment")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<?>> getPendingShipmentOrders() {
        return ResponseEntity.ok(ApiResponse.success(orderService.getPendingShipmentOrders()));
    }
}
