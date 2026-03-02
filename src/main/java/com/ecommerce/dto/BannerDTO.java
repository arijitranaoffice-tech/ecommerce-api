package com.ecommerce.dto;

import com.ecommerce.entity.BannerPosition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for Banner.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDTO {

    private String id;

    private String title;

    private String description;

    private String imageUrl;

    private String mobileImageUrl;

    private String actionUrl;

    private BannerPosition position;

    private Integer displayOrder;

    private Boolean isActive;

    private LocalDate validFrom;

    private LocalDate validUntil;

    private Integer clickCount;

    private Integer impressionCount;

    @JsonProperty("createdAt")
    private String createdAt;
}
