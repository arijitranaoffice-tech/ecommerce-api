package com.ecommerce.controller;

import com.ecommerce.entity.Category;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for category endpoints.
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryRepository.findAllActiveCategories()));
    }

    @GetMapping("/root")
    public ResponseEntity<ApiResponse<List<Category>>> getRootCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryRepository.findRootCategories()));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<ApiResponse<List<Category>>> getSubCategories(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(categoryRepository.findSubCategories(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable UUID id) {
        return categoryRepository.findById(id)
                .map(category -> ResponseEntity.ok(ApiResponse.success(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(ApiResponse.success("Category created successfully", savedCategory));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @PathVariable UUID id,
            @RequestBody Category category
    ) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    existingCategory.setDescription(category.getDescription());
                    existingCategory.setSlug(category.getSlug());
                    existingCategory.setImageUrl(category.getImageUrl());
                    existingCategory.setDisplayOrder(category.getDisplayOrder());
                    existingCategory.setActive(category.isActive());
                    return ResponseEntity.ok(ApiResponse.success("Category updated successfully", 
                            categoryRepository.save(existingCategory)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setActive(false);
                    categoryRepository.save(category);
                    return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
