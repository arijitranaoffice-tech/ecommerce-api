package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SellerOrder entity for seller order tracking.
 */
@Entity
@Table(name = "seller_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerOrder extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, unique = true)
    private String sellerOrderNumber;

    @Column(nullable = false)
    @Builder.Default
    private SellerOrderStatus status = SellerOrderStatus.PENDING;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal commissionAmount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal sellerEarnings = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private LocalDateTime approvedAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;

    @Column(length = 500)
    private String trackingNumber;

    private String carrier;

    @OneToMany(mappedBy = "sellerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SellerOrderItem> items = new ArrayList<>();

    public void addOrderItem(SellerOrderItem item) {
        items.add(item);
        item.setSellerOrder(this);
    }

    public void removeOrderItem(SellerOrderItem item) {
        items.remove(item);
        item.setSellerOrder(null);
    }
}
