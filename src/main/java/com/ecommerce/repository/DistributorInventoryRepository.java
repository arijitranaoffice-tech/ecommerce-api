package com.ecommerce.repository;

import com.ecommerce.entity.DistributorInventory;
import com.ecommerce.entity.InventoryStatus;
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
 * Repository interface for DistributorInventory entity.
 */
@Repository
public interface DistributorInventoryRepository extends JpaRepository<DistributorInventory, UUID> {

    Page<DistributorInventory> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<DistributorInventory> findByProductId(UUID productId, Pageable pageable);

    List<DistributorInventory> findByDistributorId(UUID distributorId);

    List<DistributorInventory> findByProductId(UUID productId);

    Optional<DistributorInventory> findByDistributorIdAndProductId(UUID distributorId, UUID productId);

    Optional<DistributorInventory> findByDistributorIdAndProductIdAndWarehouseId(UUID distributorId, UUID productId, UUID warehouseId);

    boolean existsByDistributorIdAndProductId(UUID distributorId, UUID productId);

    Page<DistributorInventory> findByDistributorIdAndStatus(UUID distributorId, InventoryStatus status, Pageable pageable);

    List<DistributorInventory> findByDistributorIdAndQuantityAvailableLessThanEqual(UUID distributorId, int threshold);

    @Query("SELECT di FROM DistributorInventory di WHERE di.distributor.id = :distributorId AND di.quantityAvailable <= di.reorderPoint")
    List<DistributorInventory> findLowStockInventory(@Param("distributorId") UUID distributorId);

    @Query("SELECT di FROM DistributorInventory di WHERE di.distributor.id = :distributorId AND di.quantityAvailable = 0")
    List<DistributorInventory> findOutOfStockInventory(@Param("distributorId") UUID distributorId);

    @Query("SELECT SUM(di.quantityOnHand) FROM DistributorInventory di WHERE di.distributor.id = :distributorId AND di.status = 'ACTIVE'")
    Optional<Integer> calculateTotalQuantityOnHand(@Param("distributorId") UUID distributorId);

    @Query("SELECT SUM(di.quantityAvailable) FROM DistributorInventory di WHERE di.distributor.id = :distributorId AND di.status = 'ACTIVE'")
    Optional<Integer> calculateTotalQuantityAvailable(@Param("distributorId") UUID distributorId);

    @Query("SELECT COUNT(di) FROM DistributorInventory di WHERE di.distributor.id = :distributorId AND di.quantityAvailable <= di.reorderPoint")
    long countLowStockProducts(@Param("distributorId") UUID distributorId);

    @Query("SELECT COUNT(di) FROM DistributorInventory di WHERE di.distributor.id = :distributorId AND di.quantityAvailable = 0")
    long countOutOfStockProducts(@Param("distributorId") UUID distributorId);

    @Query("SELECT di FROM DistributorInventory di WHERE di.warehouse.id = :warehouseId")
    List<DistributorInventory> findByWarehouseId(@Param("warehouseId") UUID warehouseId);
}
