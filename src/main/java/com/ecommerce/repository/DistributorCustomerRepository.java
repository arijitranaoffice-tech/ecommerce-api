package com.ecommerce.repository;

import com.ecommerce.entity.DistributorCustomer;
import com.ecommerce.entity.CustomerStatus;
import com.ecommerce.entity.CustomerTier;
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
 * Repository interface for DistributorCustomer entity.
 */
@Repository
public interface DistributorCustomerRepository extends JpaRepository<DistributorCustomer, UUID> {

    Page<DistributorCustomer> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<DistributorCustomer> findByStatus(CustomerStatus status, Pageable pageable);

    Page<DistributorCustomer> findByDistributorIdAndStatus(UUID distributorId, CustomerStatus status, Pageable pageable);

    List<DistributorCustomer> findByDistributorId(UUID distributorId);

    @Query("SELECT dc FROM DistributorCustomer dc WHERE dc.distributor.id = :distributorId AND dc.customerUser.id = :customerId")
    Optional<DistributorCustomer> findByDistributorAndCustomer(@Param("distributorId") UUID distributorId, @Param("customerId") UUID customerId);

    @Query("SELECT dc FROM DistributorCustomer dc WHERE dc.distributor.id = :distributorId AND dc.email LIKE %:email%")
    List<DistributorCustomer> findByEmailContaining(@Param("distributorId") UUID distributorId, @Param("email") String email);

    @Query("SELECT dc FROM DistributorCustomer dc WHERE dc.distributor.id = :distributorId AND dc.companyName LIKE %:keyword%")
    Page<DistributorCustomer> searchCustomers(@Param("distributorId") UUID distributorId, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT dc FROM DistributorCustomer dc WHERE dc.distributor.id = :distributorId AND dc.tier = :tier")
    List<DistributorCustomer> findByTier(@Param("distributorId") UUID distributorId, @Param("tier") CustomerTier tier);

    @Query("SELECT COUNT(dc) FROM DistributorCustomer dc WHERE dc.distributor.id = :distributorId AND dc.status = 'ACTIVE'")
    long countActiveCustomers(@Param("distributorId") UUID distributorId);

    @Query("SELECT SUM(dc.totalRevenue) FROM DistributorCustomer dc WHERE dc.distributor.id = :distributorId")
    Optional<java.math.BigDecimal> calculateTotalCustomerRevenue(@Param("distributorId") UUID distributorId);
}
