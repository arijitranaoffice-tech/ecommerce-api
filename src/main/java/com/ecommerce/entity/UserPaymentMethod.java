package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * User Payment Method entity.
 */
@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPaymentMethod extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private com.ecommerce.entity.PaymentMethodType type = com.ecommerce.entity.PaymentMethodType.CARD;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String accountNumber;

    private String expiryDate;

    private String cardholderName;

    private String billingAddress;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    private String token;

    private LocalDateTime lastUsedAt;

    @Column(length = 500)
    private String metadata;
}
