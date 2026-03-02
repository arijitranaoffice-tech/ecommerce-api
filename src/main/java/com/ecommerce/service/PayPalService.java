package com.ecommerce.service;

import com.ecommerce.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for PayPal operations.
 */
public interface PayPalService {

    PayPalOrderResponse createOrder(PayPalOrderRequest request);

    PayPalOrderResponse captureOrder(String orderId);

    PayPalOrderResponse refundOrder(String captureId, String reason);

    PayPalTransactionDTO getTransactionById(UUID transactionId);

    PayPalTransactionDTO getTransactionByPaypalOrderId(String paypalOrderId);

    Page<PayPalTransactionDTO> getUserTransactions(UUID userId, Pageable pageable);

    List<PayPalTransactionDTO> getTransactionsByStatus(String status);

    java.math.BigDecimal getTotalRevenueByDateRange(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);
}
