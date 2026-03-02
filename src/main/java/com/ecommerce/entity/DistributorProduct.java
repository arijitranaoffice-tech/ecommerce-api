package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DistributorProduct entity for product assignments to distributors.
 */
@Entity
@Table(name = "distributor_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributorProduct extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distributor_id", nullable = false)
    private Distributor distributor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal distributorPrice = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal minimumOrderQuantity = BigDecimal.ONE;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal maximumOrderQuantity = BigDecimal.valueOf(1000);

    @Column(nullable = false)
    @Builder.Default
    private Integer stockAllocation = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer reservedStock = 0;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    private LocalDateTime validFrom;

    private LocalDateTime validUntil;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal tier1Discount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal tier2Discount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal tier3Discount = BigDecimal.ZERO;

    private Integer tier1Threshold = 10;
    private Integer tier2Threshold = 50;
    private Integer tier3Threshold = 100;
}
