package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.categories.AddCategoryRequest;
import com.Steprella.Steprella.services.dtos.requests.categories.UpdateCategoryRequest;
import com.Steprella.Steprella.services.dtos.responses.categories.AddCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.ListCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.UpdateCategoryResponse;

import java.util.List;

public interface CategoryService {

    List<ListCategoryResponse> getAll();

    ListCategoryResponse getById(int id);

    AddCategoryResponse add(AddCategoryRequest request);

    UpdateCategoryResponse update(UpdateCategoryRequest request);

    void delete(int id);

    ListCategoryResponse getCategoryHierarchy(int id);
}
