package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Wishlist.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDTO {

    private String id;

    private String userId;

    private Integer itemCount;

    private List<WishlistItemDTO> items;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
