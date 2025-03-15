package com.gidcode.destinyshop.service.category;

import com.gidcode.destinyshop.exception.AlreadyExistException;
import com.gidcode.destinyshop.exception.CategoryNotFoundException;
import com.gidcode.destinyshop.model.Category;
import com.gidcode.destinyshop.repository.CategoryRepository;
import com.gidcode.destinyshop.request.AddCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new CategoryNotFoundException("Category with id "+id+" not found!")
                );
    }

    @Override
    public Category getCategoryByName(String category) {
        return categoryRepository
                .findByName(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(newCategory-> !categoryRepository.existsByName(newCategory.name()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistException(category.name()+ "already exist!"));
    }

    @Override
    public Category updateCategory(Category category, long id) {
        Category existingCategory = getCategoryById(id);
        Category newRecord = new Category(
                existingCategory.id(),
                category.name(),
                existingCategory.products()
        );
        return categoryRepository.save(newRecord);
    }

    @Override
    public void deleteCategoryById(long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> { throw new CategoryNotFoundException("Category with id "+id+" not found!");}
                );
    }
}
