package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PayPal Transaction entity.
 */
@Entity
@Table(name = "paypal_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayPalTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String paypalOrderId;

    @Column(nullable = false, unique = true)
    private String paypalPaymentId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal amount = BigDecimal.ZERO;

    private String currency = "USD";

    private String payerEmail;

    private String payerName;

    private LocalDateTime capturedAt;

    private LocalDateTime refundedAt;

    @Column(length = 500)
    private String refundReason;

    @Column(length = 2000)
    private String transactionDetails;
}
