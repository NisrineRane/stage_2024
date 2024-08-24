package net.stage.Server.Service.Impl;
import net.stage.Server.Dto.CategoryDto;
import net.stage.Server.Entity.CategoryEntity;
import net.stage.Server.Repository.CategoryRepository;
import net.stage.Server.Repository.ProductRepository;
import net.stage.Server.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public CategoryEntity addCategory(CategoryDto categoryDto) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }


    @Override
    public Optional<CategoryEntity> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity updateCategory(Long id, CategoryDto categoryDto) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            CategoryEntity category = optionalCategory.get();
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            return categoryRepository.save(category);
        } else {
            return null; // Or throw an exception if preferred
        }
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}

