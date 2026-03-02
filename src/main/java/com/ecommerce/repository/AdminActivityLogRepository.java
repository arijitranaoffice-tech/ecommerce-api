package com.ecommerce.repository;

import com.ecommerce.entity.AdminActivityLog;
import com.ecommerce.entity.ActivityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for AdminActivityLog entity.
 */
@Repository
public interface AdminActivityLogRepository extends JpaRepository<AdminActivityLog, UUID> {

    Page<AdminActivityLog> findByAdminUserId(UUID adminUserId, Pageable pageable);

    Page<AdminActivityLog> findByEntityTypeAndEntityId(String entityType, String entityId, Pageable pageable);

    List<AdminActivityLog> findByAdminUserIdOrderByCreatedAtDesc(UUID adminUserId);

    @Query("SELECT a FROM AdminActivityLog a WHERE a.entityType = :entityType ORDER BY a.createdAt DESC")
    Page<AdminActivityLog> findByEntityType(@Param("entityType") String entityType, Pageable pageable);

    @Query("SELECT COUNT(a) FROM AdminActivityLog a WHERE a.adminUser.id = :userId")
    long countByAdminUserId(@Param("userId") UUID userId);

    List<AdminActivityLog> findByStatus(ActivityStatus status, Pageable pageable);

    @Query("SELECT a FROM AdminActivityLog a WHERE a.createdAt BETWEEN :startDate AND :endDate ORDER BY a.createdAt DESC")
    Page<AdminActivityLog> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT a FROM AdminActivityLog a WHERE a.adminUser.id = :userId AND a.createdAt BETWEEN :startDate AND :endDate")
    List<AdminActivityLog> findByUserAndDateRange(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    void deleteByCreatedAtBefore(LocalDateTime date);
}
