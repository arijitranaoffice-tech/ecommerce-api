package com.ecommerce.repository;

import com.ecommerce.entity.UserPaymentMethod;
import com.ecommerce.entity.PaymentMethodType;
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
 * Repository interface for PaymentMethod entity.
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<UserPaymentMethod, UUID> {

    Page<UserPaymentMethod> findByUserId(UUID userId, Pageable pageable);

    List<UserPaymentMethod> findByUserId(UUID userId);

    List<UserPaymentMethod> findByUserIdAndIsActive(UUID userId, Boolean isActive);

    Optional<UserPaymentMethod> findByUserIdAndId(UUID userId, UUID id);

    @Query("SELECT pm FROM UserPaymentMethod pm WHERE pm.user.id = :userId AND pm.isDefault = true")
    Optional<UserPaymentMethod> findDefaultByUserId(@Param("userId") UUID userId);

    boolean existsByUserIdAndAccountNumber(UUID userId, String accountNumber);

    long countByUserId(UUID userId);

    List<UserPaymentMethod> findByType(PaymentMethodType type);
}
