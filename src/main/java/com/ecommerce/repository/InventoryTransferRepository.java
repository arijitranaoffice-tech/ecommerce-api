package com.ecommerce.repository;

import com.ecommerce.entity.InventoryTransfer;
import com.ecommerce.entity.TransferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for InventoryTransfer entity.
 */
@Repository
public interface InventoryTransferRepository extends JpaRepository<InventoryTransfer, UUID> {

    Page<InventoryTransfer> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<InventoryTransfer> findByStatus(TransferStatus status, Pageable pageable);

    List<InventoryTransfer> findByDistributorId(UUID distributorId);

    List<InventoryTransfer> findByDistributorIdAndStatus(UUID distributorId, TransferStatus status);

    @Query("SELECT it FROM InventoryTransfer it WHERE it.distributor.id = :distributorId ORDER BY it.createdAt DESC")
    Page<InventoryTransfer> findRecentTransfers(@Param("distributorId") UUID distributorId, Pageable pageable);
}
