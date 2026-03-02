package com.ecommerce.repository;

import com.ecommerce.entity.OAuth2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for OAuth2User entity.
 */
@Repository
public interface OAuth2UserRepository extends JpaRepository<OAuth2UserEntity, UUID> {

    Optional<OAuth2UserEntity> findByProviderAndProviderId(String provider, String providerId);

    Optional<OAuth2UserEntity> findByUserId(UUID userId);

    boolean existsByProviderAndProviderId(String provider, String providerId);

    @Query("SELECT o FROM OAuth2UserEntity o WHERE o.user.id = :userId AND o.isActive = true")
    Optional<OAuth2UserEntity> findActiveByUserId(@Param("userId") UUID userId);
}
