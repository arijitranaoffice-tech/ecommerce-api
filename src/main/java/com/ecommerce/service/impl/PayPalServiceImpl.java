package com.ecommerce.service.impl;

import com.ecommerce.dto.*;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.PayPalTransaction;
import com.ecommerce.entity.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PayPalTransactionRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.PayPalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of PayPalService (Mock implementation - integrate with PayPal SDK in production).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayPalServiceImpl implements PayPalService {

    private final PayPalTransactionRepository paypalTransactionRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.mode}")
    private String mode;

    @Override
    @Transactional
    public PayPalOrderResponse createOrder(PayPalOrderRequest request) {
        log.info("Creating PayPal order: {}", request);
        
        String paypalOrderId = "PAYPAL-" + UUID.randomUUID().toString().toUpperCase();
        
        PayPalTransaction transaction = PayPalTransaction.builder()
                .paypalOrderId(paypalOrderId)
                .paypalPaymentId("")
                .status("CREATED")
                .amount(new BigDecimal(request.getAmount()))
                .currency(request.getCurrency())
                .transactionDetails(request.getDescription())
                .build();
        
        transaction = paypalTransactionRepository.save(transaction);
        
        return PayPalOrderResponse.builder()
                .orderId(paypalOrderId)
                .status("CREATED")
                .amount(new BigDecimal(request.getAmount()))
                .currency(request.getCurrency())
                .approveUrl("http://localhost:3000/paypal/approve/" + paypalOrderId)
                .createTime(LocalDateTime.now().toString())
                .build();
    }

    @Override
    @Transactional
    public PayPalOrderResponse captureOrder(String orderId) {
        log.info("Capturing PayPal order: {}", orderId);
        
        PayPalTransaction transaction = paypalTransactionRepository.findByPaypalOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("PayPal order not found"));
        
        transaction.setStatus("COMPLETED");
        transaction.setCapturedAt(LocalDateTime.now());
        transaction = paypalTransactionRepository.save(transaction);
        
        return PayPalOrderResponse.builder()
                .orderId(orderId)
                .status("COMPLETED")
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .createTime(transaction.getCreatedAt().toString())
                .build();
    }

    @Override
    @Transactional
    public PayPalOrderResponse refundOrder(String captureId, String reason) {
        log.info("Refunding PayPal capture: {}, reason: {}", captureId, reason);
        
        PayPalTransaction transaction = paypalTransactionRepository.findByPaypalPaymentId(captureId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        transaction.setStatus("REFUNDED");
        transaction.setRefundedAt(LocalDateTime.now());
        transaction.setRefundReason(reason);
        transaction = paypalTransactionRepository.save(transaction);
        
        return PayPalOrderResponse.builder()
                .orderId(transaction.getPaypalOrderId())
                .status("REFUNDED")
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .build();
    }

    @Override
    public PayPalTransactionDTO getTransactionById(UUID transactionId) {
        PayPalTransaction transaction = paypalTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return mapToDTO(transaction);
    }

    @Override
    public PayPalTransactionDTO getTransactionByPaypalOrderId(String paypalOrderId) {
        PayPalTransaction transaction = paypalTransactionRepository.findByPaypalOrderId(paypalOrderId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return mapToDTO(transaction);
    }

    @Override
    public Page<PayPalTransactionDTO> getUserTransactions(UUID userId, Pageable pageable) {
        return paypalTransactionRepository.findByUserId(userId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public List<PayPalTransactionDTO> getTransactionsByStatus(String status) {
        return paypalTransactionRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return paypalTransactionRepository.calculateTotalRevenueByDateRange(startDate, endDate)
                .orElse(BigDecimal.ZERO);
    }

    private PayPalTransactionDTO mapToDTO(PayPalTransaction transaction) {
        return PayPalTransactionDTO.builder()
                .id(transaction.getId().toString())
                .orderId(transaction.getOrder() != null ? transaction.getOrder().getId().toString() : null)
                .userId(transaction.getUser() != null ? transaction.getUser().getId().toString() : null)
                .paypalOrderId(transaction.getPaypalOrderId())
                .paypalPaymentId(transaction.getPaypalPaymentId())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .payerEmail(transaction.getPayerEmail())
                .payerName(transaction.getPayerName())
                .capturedAt(transaction.getCapturedAt() != null ? transaction.getCapturedAt().toString() : null)
                .refundedAt(transaction.getRefundedAt() != null ? transaction.getRefundedAt().toString() : null)
                .createdAt(transaction.getCreatedAt() != null ? transaction.getCreatedAt().toString() : null)
                .build();
    }
}
