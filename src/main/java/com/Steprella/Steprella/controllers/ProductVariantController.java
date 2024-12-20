package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
@AllArgsConstructor
public class ProductVariantController extends BaseController{

    private final ProductVariantService productVariantService;

    @GetMapping("/get-all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> getAll(){
        List<ListProductVariantResponse> products = productVariantService.getAll();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, products);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<ListProductVariantResponse>> getById(@PathVariable int id) {
        ListProductVariantResponse product = productVariantService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, product);
    }

    @GetMapping("/filter")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> filterProducts(
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer colorId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer sizeValue) {

        List<ListProductVariantResponse> filteredProducts = productVariantService.filterProducts(brandId, colorId, categoryId, sizeValue);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, filteredProducts);
    }

    @GetMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> searchProductVariants(
            @RequestParam String searchTerm) {

        List<ListProductVariantResponse> searchedProducts = productVariantService.searchProductVariants(searchTerm);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, searchedProducts);
    }

    @PostMapping("/create-product-variant")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse<AddProductVariantResponse>> add(@RequestBody @Valid AddProductVariantRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddProductVariantResponse addProductResponse = productVariantService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addProductResponse);
    }

    @PutMapping("/update-product-variant")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse<UpdateProductVariantResponse>> update(@RequestBody @Valid UpdateProductVariantRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        UpdateProductVariantResponse updateProductResponse = productVariantService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateProductResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        productVariantService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
