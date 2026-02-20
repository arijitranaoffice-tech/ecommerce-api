package com.ecommerce.service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for Product operations.
 */
public interface ProductService {

    ProductDTO createProduct(ProductRequest request);

    ProductDTO getProductById(UUID id);

    ProductDTO getProductBySlug(String slug);

    Page<ProductDTO> getAllProducts(Pageable pageable);

    Page<ProductDTO> getActiveProducts(Pageable pageable);

    Page<ProductDTO> getProductsByCategory(UUID categoryId, Pageable pageable);

    Page<ProductDTO> searchProducts(String keyword, Pageable pageable);

    Page<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    ProductDTO updateProduct(UUID id, ProductRequest request);

    void deleteProduct(UUID id);

    void updateStock(UUID id, int quantity);

    List<ProductDTO> getLowStockProducts();

    List<ProductDTO> getOutOfStockProducts();
}
