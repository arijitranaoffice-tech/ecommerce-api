package com.ecommerce.repository;

import com.ecommerce.entity.Notification;
import com.ecommerce.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Notification entity.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Page<Notification> findByUserId(UUID userId, Pageable pageable);

    List<Notification> findByUserIdAndRead(UUID userId, Boolean read);

    Page<Notification> findByUserIdAndType(UUID userId, NotificationType type, Pageable pageable);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.read = false")
    long countUnreadByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE Notification n SET n.read = true WHERE n.user.id = :userId AND n.read = false")
    void markAllAsRead(@Param("userId") UUID userId);

    @Modifying
    @Query("DELETE FROM Notification n WHERE n.user.id = :userId AND n.read = true AND n.createdAt < :date")
    void deleteOldReadNotifications(@Param("userId") UUID userId, @Param("date") java.time.LocalDateTime date);

    List<Notification> findTop10ByUserIdOrderByCreatedAtDesc(UUID userId);
}
