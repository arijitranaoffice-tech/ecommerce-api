package com.ecommerce.service;

import com.ecommerce.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Distributor operations.
 */
public interface DistributorService {

    DistributorDTO createDistributor(CreateDistributorRequest request);

    DistributorDTO updateDistributor(UUID distributorId, UpdateDistributorRequest request);

    DistributorDTO getDistributorById(UUID distributorId);

    DistributorDTO getDistributorByUserId(UUID userId);

    DistributorDTO getDistributorByEmail(String email);

    Page<DistributorDTO> getAllDistributors(Pageable pageable);

    Page<DistributorDTO> searchDistributors(String keyword, Pageable pageable);

    void deactivateDistributor(UUID distributorId);

    void activateDistributor(UUID distributorId);

    void deleteDistributor(UUID distributorId);

    DistributorProductDTO assignProductToDistributor(UUID distributorId, UUID productId, DistributorProductRequest request);

    DistributorProductDTO updateDistributorProduct(UUID distributorProductId, DistributorProductRequest request);

    void removeProductFromDistributor(UUID distributorId, UUID productId);

    Page<DistributorProductDTO> getDistributorProducts(UUID distributorId, Pageable pageable);

    DistributorOrderDTO createDistributorOrder(UUID distributorId, CreateDistributorOrderRequest request);

    DistributorOrderDTO getDistributorOrderById(UUID orderId);

    Page<DistributorOrderDTO> getDistributorOrders(UUID distributorId, Pageable pageable);

    DistributorOrderDTO approveDistributorOrder(UUID orderId);

    DistributorOrderDTO rejectDistributorOrder(UUID orderId, String reason);

    DistributorOrderDTO shipDistributorOrder(UUID orderId, String trackingNumber, String carrier);

    DistributorOrderDTO deliverDistributorOrder(UUID orderId);

    DistributorOrderDTO cancelDistributorOrder(UUID orderId, String reason);

    DistributorCommissionDTO calculateCommission(UUID distributorId, UUID orderId);

    Page<DistributorCommissionDTO> getDistributorCommissions(UUID distributorId, Pageable pageable);

    DistributorCommissionDTO payCommission(UUID commissionId);

    DistributorInventoryDTO getDistributorInventory(UUID distributorId, UUID productId);

    Page<DistributorInventoryDTO> getDistributorInventories(UUID distributorId, Pageable pageable);

    DistributorInventoryDTO updateDistributorInventory(UUID inventoryId, DistributorInventoryUpdateRequest request);

    DistributorCustomerDTO addDistributorCustomer(UUID distributorId, CreateDistributorCustomerRequest request);

    DistributorCustomerDTO updateDistributorCustomer(UUID customerId, UpdateDistributorCustomerRequest request);

    Page<DistributorCustomerDTO> getDistributorCustomers(UUID distributorId, Pageable pageable);

    void removeDistributorCustomer(UUID distributorId, UUID customerId);

    WarehouseDTO createWarehouse(UUID distributorId, CreateWarehouseRequest request);

    WarehouseDTO updateWarehouse(UUID warehouseId, UpdateWarehouseRequest request);

    Page<WarehouseDTO> getDistributorWarehouses(UUID distributorId, Pageable pageable);

    InventoryTransferDTO transferInventory(InventoryTransferRequest request);
}
