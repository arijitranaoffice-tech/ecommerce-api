package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Banner/Advertisement entity for homepage and promotions.
 */
@Entity
@Table(name = "banners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    private String mobileImageUrl;

    @Column(length = 500)
    private String actionUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private BannerPosition position = BannerPosition.HOME_PAGE;

    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    private LocalDate validFrom;

    private LocalDate validUntil;

    @Column(length = 500)
    private String targetAudience;

    @Column(nullable = false)
    @Builder.Default
    private Integer clickCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer impressionCount = 0;
}
