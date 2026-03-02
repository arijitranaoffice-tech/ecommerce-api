package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Admin Activity Log entity for audit trail.
 */
@Entity
@Table(name = "admin_activity_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminActivityLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id", nullable = false)
    private User adminUser;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String entityType;

    private String entityId;

    @Column(length = 2000)
    private String description;

    @Column(length = 4000)
    private String changes;

    @Column(nullable = false)
    @Builder.Default
    private String ipAddress = "unknown";

    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ActivityStatus status = ActivityStatus.SUCCESS;

    private String errorMessage;

    @Column(length = 2000)
    private String metadata;
}
