package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for sales by date.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesByDateDTO {

    private String date;

    private BigDecimal revenue;

    private Integer orders;
}
