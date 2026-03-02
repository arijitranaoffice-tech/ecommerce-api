package com.ecommerce.repository;

import com.ecommerce.entity.Shipment;
import com.ecommerce.entity.ShipmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Shipment entity.
 */
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {

    Optional<Shipment> findByTrackingNumber(String trackingNumber);

    Optional<Shipment> findByOrderId(UUID orderId);

    Page<Shipment> findByStatus(ShipmentStatus status, Pageable pageable);

    List<Shipment> findByStatus(ShipmentStatus status);

    @Query("SELECT s FROM Shipment s WHERE s.trackingNumber = :trackingNumber")
    Optional<Shipment> getByTrackingNumber(@Param("trackingNumber") String trackingNumber);

    @Query("SELECT s FROM Shipment s WHERE s.status = 'IN_TRANSIT'")
    List<Shipment> findInTransitShipments();

    @Query("SELECT s FROM Shipment s WHERE s.status = 'OUT_FOR_DELIVERY'")
    List<Shipment> findOutForDeliveryShipments();
}
