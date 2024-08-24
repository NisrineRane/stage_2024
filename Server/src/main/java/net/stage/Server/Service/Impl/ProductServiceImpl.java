package net.stage.Server.Service.Impl;

import jakarta.persistence.EntityNotFoundException;
import net.stage.Server.Dto.ProductDto;
import net.stage.Server.Entity.CategoryEntity;
import net.stage.Server.Entity.ProductEntity;
import net.stage.Server.Repository.CategoryRepository;
import net.stage.Server.Repository.ProductRepository;
import net.stage.Server.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;  // Injectez le repository de catégorie

    public ProductEntity addProduct(ProductDto productDto) {
        // Fetch the category from the repository
        CategoryEntity category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        ProductEntity product = new ProductEntity();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setCategory(category);  // Set the category here

        return productRepository.save(product);
    }


    @Override
    public Optional<ProductEntity> getProduct(Long id) {
        return Optional.ofNullable(productRepository.findById(id).orElse(null));
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity updateProduct(Long id, ProductDto productDto) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setStockQuantity(productDto.getStockQuantity());
            // Assurez-vous d'ajouter la gestion de la catégorie si nécessaire
            // Ex: product.setCategoryEntity(categoryRepository.findById(productDto.getCategoryId()).orElse(null));
            return productRepository.save(product);
        }
        throw new IllegalArgumentException("Product not found");
    }

    @Override
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }
}
