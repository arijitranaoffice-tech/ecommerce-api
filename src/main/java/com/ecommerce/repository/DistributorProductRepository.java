package com.ecommerce.repository;

import com.ecommerce.entity.DistributorProduct;
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
 * Repository interface for DistributorProduct entity.
 */
@Repository
public interface DistributorProductRepository extends JpaRepository<DistributorProduct, UUID> {

    Page<DistributorProduct> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<DistributorProduct> findByProductId(UUID productId, Pageable pageable);

    List<DistributorProduct> findByDistributorId(UUID distributorId);

    List<DistributorProduct> findByProductId(UUID productId);

    Optional<DistributorProduct> findByDistributorIdAndProductId(UUID distributorId, UUID productId);

    boolean existsByDistributorIdAndProductId(UUID distributorId, UUID productId);

    @Query("SELECT dp FROM DistributorProduct dp WHERE dp.distributor.id = :distributorId AND dp.active = true")
    Page<DistributorProduct> findActiveProductsByDistributor(
            @Param("distributorId") UUID distributorId,
            Pageable pageable
    );

    @Query("SELECT dp FROM DistributorProduct dp WHERE dp.distributor.id = :distributorId AND dp.product.id = :productId AND dp.active = true")
    Optional<DistributorProduct> findActiveByDistributorAndProduct(
            @Param("distributorId") UUID distributorId,
            @Param("productId") UUID productId
    );

    @Query("SELECT COUNT(dp) FROM DistributorProduct dp WHERE dp.distributor.id = :distributorId AND dp.active = true")
    long countActiveProductsByDistributor(@Param("distributorId") UUID distributorId);

    @Query("SELECT dp FROM DistributorProduct dp WHERE dp.stockAllocation > 0 AND dp.distributor.id = :distributorId")
    List<DistributorProduct> findAllocatedProducts(@Param("distributorId") UUID distributorId);
}
