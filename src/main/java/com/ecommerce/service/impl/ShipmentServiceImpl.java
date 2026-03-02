package com.ecommerce.service.impl;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.SellerPortalMapper;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ShipmentRepository;
import com.ecommerce.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of ShipmentService.
 */
@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;
    private final SellerPortalMapper sellerPortalMapper;

    @Override
    @Transactional
    public ShipmentDTO createShipment(CreateShipmentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", request.getOrderId()));

        Shipment shipment = Shipment.builder()
                .order(order)
                .trackingNumber(request.getTrackingNumber())
                .carrier(request.getCarrier())
                .status(ShipmentStatus.PENDING)
                .shippingAddress(request.getShippingAddress())
                .shipmentNotes(request.getShipmentNotes())
                .trackingEvents(List.of("Shipment created"))
                .build();

        shipment = shipmentRepository.save(shipment);
        return sellerPortalMapper.toShipmentDTO(shipment);
    }

    @Override
    @Transactional
    public ShipmentDTO updateShipment(UUID shipmentId, UpdateShipmentStatusRequest request) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", shipmentId));

        if (request.getStatus() != null) {
            shipment.setStatus(request.getStatus());
            shipment.getTrackingEvents().add("Status updated to: " + request.getStatus());
        }
        if (request.getTrackingNumber() != null) shipment.setTrackingNumber(request.getTrackingNumber());
        if (request.getCarrier() != null) shipment.setCarrier(request.getCarrier());
        if (request.getExceptionReason() != null) shipment.setExceptionReason(request.getExceptionReason());
        if (request.getShipmentNotes() != null) shipment.setShipmentNotes(request.getShipmentNotes());

        shipment = shipmentRepository.save(shipment);
        return sellerPortalMapper.toShipmentDTO(shipment);
    }

    @Override
    public ShipmentDTO getShipmentById(UUID shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", shipmentId));
        return sellerPortalMapper.toShipmentDTO(shipment);
    }

    @Override
    public ShipmentDTO getShipmentByTrackingNumber(String trackingNumber) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", trackingNumber));
        return sellerPortalMapper.toShipmentDTO(shipment);
    }

    @Override
    public ShipmentDTO getShipmentByOrderId(UUID orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment for order", orderId));
        return sellerPortalMapper.toShipmentDTO(shipment);
    }

    @Override
    public Page<ShipmentDTO> getAllShipments(Pageable pageable) {
        return shipmentRepository.findAll(pageable).map(sellerPortalMapper::toShipmentDTO);
    }

    @Override
    public Page<ShipmentDTO> getShipmentsByStatus(String status, Pageable pageable) {
        return shipmentRepository.findByStatus(ShipmentStatus.valueOf(status), pageable)
                .map(sellerPortalMapper::toShipmentDTO);
    }

    @Override
    public List<ShipmentDTO> getInTransitShipments() {
        return shipmentRepository.findInTransitShipments()
                .stream()
                .map(sellerPortalMapper::toShipmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShipmentDTO> getOutForDeliveryShipments() {
        return shipmentRepository.findOutForDeliveryShipments()
                .stream()
                .map(sellerPortalMapper::toShipmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateShipmentStatus(UUID shipmentId, String status) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", shipmentId));
        shipment.setStatus(ShipmentStatus.valueOf(status));
        shipmentRepository.save(shipment);
    }

    @Override
    @Transactional
    public void markAsDelivered(UUID shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", shipmentId));
        shipment.setStatus(ShipmentStatus.DELIVERED);
        shipment.setDeliveredAt(LocalDateTime.now());
        shipment.getTrackingEvents().add("Delivered");
        shipmentRepository.save(shipment);
    }

    @Override
    @Transactional
    public void markAsException(UUID shipmentId, String reason) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", shipmentId));
        shipment.setStatus(ShipmentStatus.EXCEPTION);
        shipment.setExceptionAt(LocalDateTime.now());
        shipment.setExceptionReason(reason);
        shipment.getTrackingEvents().add("Exception: " + reason);
        shipmentRepository.save(shipment);
    }

    @Override
    @Transactional
    public void deleteShipment(UUID shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", shipmentId));
        shipmentRepository.delete(shipment);
    }
}
