package com.ecommerce.repository;

import com.ecommerce.entity.Warehouse;
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
 * Repository interface for Warehouse entity.
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    Optional<Warehouse> findByCode(String code);

    boolean existsByCode(String code);

    Page<Warehouse> findByDistributorId(UUID distributorId, Pageable pageable);

    List<Warehouse> findByDistributorId(UUID distributorId);

    @Query("SELECT w FROM Warehouse w WHERE w.distributor.id = :distributorId AND w.isActive = true")
    Page<Warehouse> findActiveWarehouses(@Param("distributorId") UUID distributorId, Pageable pageable);

    @Query("SELECT COUNT(w) FROM Warehouse w WHERE w.distributor.id = :distributorId AND w.isActive = true")
    long countActiveWarehouses(@Param("distributorId") UUID distributorId);
}
