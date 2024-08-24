package net.stage.Server.Controller;

import net.stage.Server.Dto.ProductDto;
import net.stage.Server.Entity.ProductEntity;
import net.stage.Server.Response.ApiResponse;
import net.stage.Server.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductEntity>> createProduct(@RequestBody ProductDto productDto) {
        ProductEntity createdProduct = productService.addProduct(productDto);
        ApiResponse<ProductEntity> response = ApiResponse.<ProductEntity>builder()
                .message("Product created successfully")
                .code(201) // HTTP status code for created
                .data(createdProduct)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductEntity>> getProductById(@PathVariable Long id) {
        Optional<ProductEntity> product = productService.getProduct(id);
        if (product.isPresent()) {
            ApiResponse<ProductEntity> response = ApiResponse.<ProductEntity>builder()
                    .message("Product retrieved successfully")
                    .code(200) // HTTP status code for OK
                    .data(product.get())
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<ProductEntity> response = ApiResponse.<ProductEntity>builder()
                    .message("Product not found")
                    .code(404) // HTTP status code for Not Found
                    .error("Product with ID " + id + " does not exist")
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductEntity>>> getAllProducts() {
        List<ProductEntity> products = productService.getAllProducts();
        ApiResponse<List<ProductEntity>> response = ApiResponse.<List<ProductEntity>>builder()
                .message("Products retrieved successfully")
                .code(200)
                .data(products)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductEntity>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto
    ) {
        ProductEntity updatedProduct = productService.updateProduct(id, productDto);
        if (updatedProduct != null) {
            ApiResponse<ProductEntity> response = ApiResponse.<ProductEntity>builder()
                    .message("Product updated successfully")
                    .code(200)
                    .data(updatedProduct)
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<ProductEntity> response = ApiResponse.<ProductEntity>builder()
                    .message("Product not found")
                    .code(404)
                    .error("Product with ID " + id + " does not exist")
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Product deleted successfully")
                .code(204) // HTTP status code for No Content
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.noContent().build();
    }
}
