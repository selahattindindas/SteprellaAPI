package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.CategoryService;
import com.Steprella.Steprella.services.dtos.requests.categories.AddCategoryRequest;
import com.Steprella.Steprella.services.dtos.requests.categories.UpdateCategoryRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.AddCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.categories.UpdateCategoryResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-categories")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminCategoryController extends BaseController {

    private final CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<BaseResponse<AddCategoryResponse>> add(@RequestBody @Valid AddCategoryRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddCategoryResponse addCategoryResponse = categoryService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addCategoryResponse);
    }

    @PutMapping("/update-category")
    public ResponseEntity<BaseResponse<UpdateCategoryResponse>> update(@RequestBody @Valid UpdateCategoryRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        UpdateCategoryResponse updateCategoryResponse = categoryService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateCategoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        categoryService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
