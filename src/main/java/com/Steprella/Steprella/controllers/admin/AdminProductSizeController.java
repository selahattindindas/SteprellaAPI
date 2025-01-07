package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductSizeService;
import com.Steprella.Steprella.services.dtos.requests.productsizes.AddProductSizeRequest;
import com.Steprella.Steprella.services.dtos.requests.productsizes.UpdateProductSizeRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.AddProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.UpdateProductSizeResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-product-sizes")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminProductSizeController extends BaseController {

    private final ProductSizeService productSizeService;

    @PostMapping("/create-product-size")
    public ResponseEntity<BaseResponse<AddProductSizeResponse>> add(@RequestBody @Valid AddProductSizeRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddProductSizeResponse addProductSize = productSizeService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addProductSize);
    }

    @PutMapping("/update-product-size")
    public ResponseEntity<BaseResponse<UpdateProductSizeResponse>> update(@RequestBody @Valid UpdateProductSizeRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        UpdateProductSizeResponse updateProductSize = productSizeService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateProductSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        productSizeService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
