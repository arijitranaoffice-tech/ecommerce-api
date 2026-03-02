package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Shipment entity for order tracking.
 */
@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, unique = true)
    private String trackingNumber;

    @Column(nullable = false)
    private String carrier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ShipmentStatus status = ShipmentStatus.PENDING;

    private String shippingAddress;

    private LocalDateTime pickedUpAt;
    private LocalDateTime inTransitAt;
    private LocalDateTime outForDeliveryAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime exceptionAt;

    @Column(length = 500)
    private String exceptionReason;

    @Column(length = 2000)
    private String shipmentNotes;

    @ElementCollection
    @CollectionTable(name = "shipment_tracking_events", joinColumns = @JoinColumn(name = "shipment_id"))
    @Column(name = "event")
    @Builder.Default
    private java.util.List<String> trackingEvents = new java.util.ArrayList<>();
}
