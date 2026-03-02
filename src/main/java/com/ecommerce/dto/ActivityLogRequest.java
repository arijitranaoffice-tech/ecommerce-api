package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for logging admin activity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLogRequest {

    @NotBlank(message = "Action is required")
    private String action;

    @NotBlank(message = "Entity type is required")
    private String entityType;

    private String entityId;

    private String description;

    private String changes;

    private String ipAddress;

    private String userAgent;

    private String metadata;
}
