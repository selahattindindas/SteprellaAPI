package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Category;
import com.Steprella.Steprella.services.abstracts.CategoryService;
import com.Steprella.Steprella.services.dtos.requests.categories.AddCategoryRequest;
import com.Steprella.Steprella.services.dtos.requests.categories.UpdateCategoryRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.AddCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.ListCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.UpdateCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController{

    private final CategoryService categoryService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListCategoryResponse>>> getAll(){
        List<ListCategoryResponse> categories = categoryService.getAll();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListCategoryResponse>> getById(@PathVariable int id) {
        ListCategoryResponse category = categoryService.getById(id);

        if (category == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_CATEGORY_NOT_FOUND, null);
        } else {
            return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, category);
        }
    }

    @PostMapping("/create-category")
    public ResponseEntity<BaseResponse<AddCategoryResponse>> add(@RequestBody @Valid AddCategoryRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        Category parentCategory = null;
        if (request.getParentId() != null) {
            parentCategory = categoryService.getCategoryByParentId(request.getParentId());
            if (parentCategory == null) {
                return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_PARENT_NOT_FOUND, null);
            }
        }

        AddCategoryResponse addCategoryResponse = categoryService.add(request, parentCategory);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addCategoryResponse);
    }

    @PutMapping("/update-category")
    public ResponseEntity<BaseResponse<UpdateCategoryResponse>> update(@RequestBody @Valid UpdateCategoryRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }
        Category existingCategory = categoryService.getCategoryById(request.getId());
            if(existingCategory == null)
                sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_CATEGORY_NOT_FOUND, null);

        Category parentCategory = null;
        if (request.getParentId() != null) {
            parentCategory = categoryService.getCategoryByParentId(request.getParentId());
            if (parentCategory == null) {
                return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_PARENT_NOT_FOUND, null);
            }
        }
        assert existingCategory != null;
        existingCategory.setParent(parentCategory);

        UpdateCategoryResponse updateCategoryResponse = categoryService.update(request, existingCategory);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateCategoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        categoryService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
