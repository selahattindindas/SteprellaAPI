package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.Category;
import com.Steprella.Steprella.services.dtos.requests.categories.AddCategoryRequest;
import com.Steprella.Steprella.services.dtos.requests.categories.UpdateCategoryRequest;
import com.Steprella.Steprella.services.dtos.responses.categories.AddCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.ListCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.UpdateCategoryResponse;

import java.util.List;

public interface CategoryService {

    List<ListCategoryResponse> getAll();

    ListCategoryResponse getById(int id);

    AddCategoryResponse add(AddCategoryRequest request, Category category);

    UpdateCategoryResponse update(UpdateCategoryRequest request, Category category);

    void delete(int id);

    Category getCategoryById(int id);

    Category getCategoryByParentId(Integer parentId);

}
