package net.stage.Server.Service;

import net.stage.Server.Dto.ProductDto;
import net.stage.Server.Entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     * Adds a new product to the system.
     *
     * @param productDto the DTO containing product details
     * @return the created ProductEntity
     */
    ProductEntity addProduct(ProductDto productDto);

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return an Optional containing the found ProductEntity, or empty if not found
     */
    Optional<ProductEntity> getProduct(Long id);

    /**
     * Retrieves all products.
     *
     * @return a list of all ProductEntities
     */
    List<ProductEntity> getAllProducts();

    /**
     * Updates an existing product.
     *
     * @param id the ID of the product to update
     * @param productDto the DTO containing updated product details
     * @return the updated ProductEntity
     * @throws IllegalArgumentException if the product is not found
     */
    ProductEntity updateProduct(Long id, ProductDto productDto);

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @throws IllegalArgumentException if the product is not found
     */
    void deleteProduct(Long id);
}
