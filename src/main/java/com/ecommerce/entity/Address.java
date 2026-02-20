package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Address entity for user shipping and billing addresses.
 */
@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String street;

    private String street2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    @Builder.Default
    private AddressType type = AddressType.SHIPPING;

    @Column(nullable = false)
    @Builder.Default
    private boolean isDefault = false;

    private String phone;
}
