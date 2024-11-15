package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.CategoryService;
import com.Steprella.Steprella.services.dtos.requests.categories.AddCategoryRequest;
import com.Steprella.Steprella.services.dtos.requests.categories.UpdateCategoryRequest;
import com.Steprella.Steprella.services.dtos.responses.categories.AddCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.ListCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.UpdateCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<ListCategoryResponse> getAll() {
        return List.of();
    }

    @Override
    public ListCategoryResponse getById(int id) {
        return null;
    }

    @Override
    public AddCategoryResponse add(AddCategoryRequest request) {
        return null;
    }

    @Override
    public UpdateCategoryResponse update(UpdateCategoryRequest request) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
