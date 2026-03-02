package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.PayPalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for PayPal payment endpoints.
 */
@RestController
@RequestMapping("/payments/paypal")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PayPalController {

    private final PayPalService payPalService;

    @PostMapping("/create-order")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<PayPalOrderResponse>> createOrder(@Valid @RequestBody PayPalOrderRequest request) {
        return ResponseEntity.ok(ApiResponse.success("PayPal order created", payPalService.createOrder(request)));
    }

    @PostMapping("/capture-order/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<PayPalOrderResponse>> captureOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(ApiResponse.success("Payment captured", payPalService.captureOrder(orderId)));
    }

    @PostMapping("/refund/{captureId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<PayPalOrderResponse>> refundOrder(@PathVariable String captureId, @RequestParam String reason) {
        return ResponseEntity.ok(ApiResponse.success("Payment refunded", payPalService.refundOrder(captureId, reason)));
    }

    @GetMapping("/transaction/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<PayPalTransactionDTO>> getTransaction(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(payPalService.getTransactionById(id)));
    }

    @GetMapping("/transaction/paypal/{paypalOrderId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<PayPalTransactionDTO>> getTransactionByPaypalOrderId(@PathVariable String paypalOrderId) {
        return ResponseEntity.ok(ApiResponse.success(payPalService.getTransactionByPaypalOrderId(paypalOrderId)));
    }

    @GetMapping("/user/{userId}/transactions")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<PayPalTransactionDTO>>> getUserTransactions(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(payPalService.getUserTransactions(userId, pageable)));
    }

    @GetMapping("/transactions/status/{status}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<PayPalTransactionDTO>>> getTransactionsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(ApiResponse.success(payPalService.getTransactionsByStatus(status)));
    }

    @GetMapping("/revenue/range")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<java.math.BigDecimal>> getTotalRevenueByDateRange(
            @RequestParam java.time.LocalDateTime startDate,
            @RequestParam java.time.LocalDateTime endDate) {
        return ResponseEntity.ok(ApiResponse.success(payPalService.getTotalRevenueByDateRange(startDate, endDate)));
    }
}
