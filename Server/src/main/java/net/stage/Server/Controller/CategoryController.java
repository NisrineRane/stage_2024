package net.stage.Server.Controller;

import net.stage.Server.Dto.CategoryDto;
import net.stage.Server.Entity.CategoryEntity;
import net.stage.Server.Response.ApiResponse;
import net.stage.Server.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryEntity>> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryEntity category = categoryService.addCategory(categoryDto);
        ApiResponse<CategoryEntity> response = ApiResponse.<CategoryEntity>builder()
                .message("Category created successfully")
                .code(201) // HTTP status code for created
                .data(category)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryEntity>> getCategoryById(@PathVariable Long id) {
        Optional<CategoryEntity> category = categoryService.getCategory(id);
        if (category.isPresent()) {
            ApiResponse<CategoryEntity> response = ApiResponse.<CategoryEntity>builder()
                    .message("Category retrieved successfully")
                    .code(200) // HTTP status code for OK
                    .data(category.get())
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<CategoryEntity> response = ApiResponse.<CategoryEntity>builder()
                    .message("Category not found")
                    .code(404) // HTTP status code for Not Found
                    .error("Category with ID " + id + " does not exist")
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryEntity>>> getAllCategories() {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        ApiResponse<List<CategoryEntity>> response = ApiResponse.<List<CategoryEntity>>builder()
                .message("Categories retrieved successfully")
                .code(200)
                .data(categories)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryEntity>> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto
    ) {
        CategoryEntity updatedCategory = categoryService.updateCategory(id, categoryDto);
        if (updatedCategory != null) {
            ApiResponse<CategoryEntity> response = ApiResponse.<CategoryEntity>builder()
                    .message("Category updated successfully")
                    .code(200)
                    .data(updatedCategory)
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<CategoryEntity> response = ApiResponse.<CategoryEntity>builder()
                    .message("Category not found")
                    .code(404)
                    .error("Category with ID " + id + " does not exist")
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Category deleted successfully")
                .code(204) // HTTP status code for No Content
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.noContent().build();
    }
}
