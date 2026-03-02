package com.ecommerce.repository;

import com.ecommerce.entity.EmailLog;
import com.ecommerce.entity.EmailStatus;
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
 * Repository for EmailLog entity.
 */
@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, UUID> {

    Page<EmailLog> findByRecipient(String recipient, Pageable pageable);

    Page<EmailLog> findByStatus(EmailStatus status, Pageable pageable);

    List<EmailLog> findByMessageType(String messageType);

    @Query("SELECT COUNT(e) FROM EmailLog e WHERE e.recipient = :recipient AND e.status = 'SENT'")
    long countSentEmailsByRecipient(@Param("recipient") String recipient);

    @Query("SELECT e FROM EmailLog e WHERE e.status = 'PENDING'")
    List<EmailLog> findPendingEmails();

    @Query("SELECT COUNT(e) FROM EmailLog e WHERE e.sentAt BETWEEN :startDate AND :endDate")
    long countEmailsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
