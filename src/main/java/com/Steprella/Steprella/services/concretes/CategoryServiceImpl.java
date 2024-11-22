package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.Category;
import com.Steprella.Steprella.repositories.CategoryRepository;
import com.Steprella.Steprella.services.abstracts.CategoryService;
import com.Steprella.Steprella.services.dtos.requests.categories.AddCategoryRequest;
import com.Steprella.Steprella.services.dtos.requests.categories.UpdateCategoryRequest;
import com.Steprella.Steprella.services.dtos.responses.categories.AddCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.ListCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.UpdateCategoryResponse;
import com.Steprella.Steprella.services.mappers.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public List<ListCategoryResponse> getAll() {
        List<Category> rootCategories = categoryRepository.findRootCategories();
        return rootCategories.stream()
                .map(CategoryMapper.INSTANCE::listResponseFromCategory)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListCategoryResponse> getById(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        List<ListCategoryResponse> parentCategories = new ArrayList<>();
        Category currentCategory = category;

        while (currentCategory != null && currentCategory.getParent() != null) {
            currentCategory = currentCategory.getParent();
            ListCategoryResponse parentResponse = CategoryMapper.INSTANCE.listResponseFromCategory(currentCategory);
            parentCategories.addFirst(parentResponse);
        }

        return parentCategories;
    }

    @Override
    public AddCategoryResponse add(AddCategoryRequest request, Category parentCategory) {
        Category newCategory = CategoryMapper.INSTANCE.categoryFromAddRequest(request, parentCategory);
        Category savedCategory = categoryRepository.save(newCategory);

        return CategoryMapper.INSTANCE.addResponseCategory(savedCategory);
    }

    @Override
    public UpdateCategoryResponse update(UpdateCategoryRequest request, Category category) {
        Category updatedCategory = CategoryMapper.INSTANCE.categoryFromUpdateRequest(request, category);

        Category savedCategory = categoryRepository.save(updatedCategory);

        return CategoryMapper.INSTANCE.updateResponseFromCategory(savedCategory);
    }

    @Override
    public void delete(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        assert category != null;
        categoryRepository.delete(category);
    }

    public Category getCategoryByParentId(Integer parentId) {
        return categoryRepository.findById(parentId).orElse(null);
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<ListCategoryResponse> getCategoryHierarchy(Category category) {
        List<ListCategoryResponse> parentCategories = new ArrayList<>();
        Category currentCategory = category;

        while (currentCategory != null && currentCategory.getParent() != null) {
            currentCategory = currentCategory.getParent();
            ListCategoryResponse parentResponse = CategoryMapper.INSTANCE.listResponseFromCategory(currentCategory);
            parentCategories.addFirst(parentResponse);
        }

        return parentCategories;
    }
}
