package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductService;
import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-products")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminProductController extends BaseController {

    private final ProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity<BaseResponse<AddProductResponse>> add(@RequestBody @Valid AddProductRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        AddProductResponse addProductResponse = productService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addProductResponse);
    }

    @PutMapping("/update-product")
    public ResponseEntity<BaseResponse<UpdateProductResponse>> update(@RequestBody @Valid UpdateProductRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        UpdateProductResponse updateProductResponse = productService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateProductResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        productService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
