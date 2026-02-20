package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Review entity for product reviews.
 */
@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Builder.Default
    private Integer rating = 5;

    @Column(length = 2000)
    private String title;

    @Column(length = 5000)
    private String comment;

    @Column(nullable = false)
    @Builder.Default
    private boolean verified = false;

    @Column(nullable = false)
    @Builder.Default
    private ReviewStatus status = ReviewStatus.PENDING;

    private String adminResponse;

    private Integer helpfulCount;

    @Column(nullable = false)
    @Builder.Default
    private boolean approved = false;
}
