package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<ListCategoryResponse> getAll() {
        List<Category> rootCategories = categoryRepository.findByParentIsNull();
        return rootCategories.stream()
                .map(CategoryMapper.INSTANCE::listResponseFromCategory)
                .collect(Collectors.toList());
    }

    @Override
    public ListCategoryResponse getById(int id) {
        Category category = findCategoryById(id);
        return CategoryMapper.INSTANCE.listResponseFromCategory(category);
    }

    @Override
    public AddCategoryResponse add(AddCategoryRequest request) {
        Category parentCategory = null;

        if (request.getParentId() != null) {
            if (request.getParentId() == 0) {
                parentCategory = null;
            } else {
                parentCategory = findCategoryById(request.getParentId());
            }
        }

        Category addCategory = CategoryMapper.INSTANCE.categoryFromAddRequest(request, parentCategory);
        Category savedCategory = categoryRepository.save(addCategory);

        return CategoryMapper.INSTANCE.addResponseCategory(savedCategory);
    }

    @Override
    public UpdateCategoryResponse update(UpdateCategoryRequest request) {
        Category category = findCategoryById(request.getId());

        Category parentCategory = null;
        if (request.getParentId() != null) {
            if (request.getParentId() == 0) {
                parentCategory = null;
            } else {
                parentCategory = findCategoryById(request.getParentId());
            }
        }

        Category updateCategory = CategoryMapper.INSTANCE.categoryFromUpdateRequest(request, category);
        Category savedCategory = categoryRepository.save(updateCategory);
        updateCategory.setParent(parentCategory);

        return CategoryMapper.INSTANCE.updateResponseFromCategory(savedCategory);
    }

    @Override
    public void delete(int id) {
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public ListCategoryResponse getCategoryHierarchy(int id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            return buildCategoryHierarchy(category);
        } else {
            return null;
        }
    }

    private ListCategoryResponse buildCategoryHierarchy(Category category) {
        ListCategoryResponse response = CategoryMapper.INSTANCE.listResponseFromCategory(category);

        if (category.getParent() != null) {
            response.setParentId(category.getParent().getId());
            response.setChildren(new ArrayList<>());
            ListCategoryResponse parentResponse = buildCategoryHierarchy(category.getParent());
            response.getChildren().add(parentResponse);
        } else {
            response.setChildren(new ArrayList<>());
        }

        return response;
    }

    private Category findCategoryById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_CATEGORY_NOT_FOUND));
    }
}
