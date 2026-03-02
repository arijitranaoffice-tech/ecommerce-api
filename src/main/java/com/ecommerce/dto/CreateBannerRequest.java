package com.ecommerce.dto;

import com.ecommerce.entity.BannerPosition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request DTO for creating Banner.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBannerRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private String mobileImageUrl;

    private String actionUrl;

    @Builder.Default
    private BannerPosition position = BannerPosition.HOME_PAGE;

    @Builder.Default
    private Integer displayOrder = 0;

    private LocalDate validFrom;

    private LocalDate validUntil;

    private String targetAudience;
}
