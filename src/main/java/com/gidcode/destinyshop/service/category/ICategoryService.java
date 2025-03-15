package com.gidcode.destinyshop.service.category;

import com.gidcode.destinyshop.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(long id);
    Category getCategoryByName(String category);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, long id);
    void deleteCategoryById(long id);
}
