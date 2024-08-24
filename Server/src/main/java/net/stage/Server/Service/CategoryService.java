package net.stage.Server.Service;

import net.stage.Server.Dto.CategoryDto;
import net.stage.Server.Entity.CategoryEntity;
import java.util.*;

public interface CategoryService {
    CategoryEntity addCategory(CategoryDto categoryDto);
    Optional<CategoryEntity> getCategory(Long id);
    List<CategoryEntity> getAllCategories();
    CategoryEntity updateCategory(Long id, CategoryDto categoryDto);
    void deleteCategory(Long id);
}
