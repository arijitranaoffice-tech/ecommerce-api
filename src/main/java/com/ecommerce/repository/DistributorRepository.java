package com.ecommerce.repository;

import com.ecommerce.entity.Distributor;
import com.ecommerce.entity.DistributorStatus;
import com.ecommerce.entity.DistributorTier;
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
 * Repository interface for Distributor entity.
 */
@Repository
public interface DistributorRepository extends JpaRepository<Distributor, UUID> {

    Optional<Distributor> findByDistributorCode(String distributorCode);

    Optional<Distributor> findByUserId(UUID userId);

    boolean existsByDistributorCode(String distributorCode);

    boolean existsByUserId(UUID userId);

    Page<Distributor> findByStatus(DistributorStatus status, Pageable pageable);

    Page<Distributor> findByTier(DistributorTier tier, Pageable pageable);

    @Query("SELECT d FROM Distributor d WHERE d.companyName LIKE %:keyword% OR d.user.firstName LIKE %:keyword% OR d.user.lastName LIKE %:keyword%")
    Page<Distributor> searchDistributors(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT d FROM Distributor d WHERE d.status = 'ACTIVE'")
    Page<Distributor> findActiveDistributors(Pageable pageable);

    @Query("SELECT d FROM Distributor d WHERE d.tier = :tier AND d.status = 'ACTIVE'")
    List<Distributor> findByTierAndActive(@Param("tier") DistributorTier tier);

    @Query("SELECT d FROM Distributor d WHERE d.user.email = :email")
    Optional<Distributor> findByUserEmail(@Param("email") String email);

    boolean existsByUserEmail(@Param("email") String email);

    @Query("SELECT d FROM Distributor d WHERE :territoryCode MEMBER OF d.territories AND d.status = 'ACTIVE'")
    List<Distributor> findByTerritory(@Param("territoryCode") String territoryCode);

    @Query("SELECT COUNT(d) FROM Distributor d WHERE d.status = 'ACTIVE'")
    long countActiveDistributors();

    @Query("SELECT d FROM Distributor d WHERE d.currentBalance > d.creditLimit")
    List<Distributor> findOverCreditLimitDistributors();
}
