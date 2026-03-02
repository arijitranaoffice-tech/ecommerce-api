package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Wishlist entity for user saved products.
 */
@Entity
@Table(name = "wishlists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wishlist extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WishlistItem> items = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Integer itemCount = 0;

    public void addItem(WishlistItem item) {
        items.add(item);
        item.setWishlist(this);
        itemCount = items.size();
    }

    public void removeItem(WishlistItem item) {
        items.remove(item);
        item.setWishlist(null);
        itemCount = items.size();
    }
}
