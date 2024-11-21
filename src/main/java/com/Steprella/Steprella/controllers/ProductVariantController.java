package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ColorService;
import com.Steprella.Steprella.services.abstracts.ProductService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
@RequiredArgsConstructor
public class ProductVariantController extends BaseController{

    private final ProductVariantService productVariantService;
    private final ColorService colorService;
    private final ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> getAll(){
        List<ListProductVariantResponse> products = productVariantService.getAll();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListProductVariantResponse>> getById(@PathVariable int id) {
        ListProductVariantResponse product = productVariantService.getById(id);
        if (product == null){
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_PRODUCT_NOT_FOUND, null);
        }
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, product);
    }

    @GetMapping("/filter")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> filterProducts(
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer colorId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer sizeId) {

        List<ListProductVariantResponse> filteredProducts = productVariantService.filterProducts(brandId, colorId, categoryId, sizeId);

        if (filteredProducts.isEmpty()) {
            return sendResponse(HttpStatus.NO_CONTENT, Messages.Error.CUSTOM_PRODUCT_NOT_FOUND, null);
        }

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, filteredProducts);
    }

    @PostMapping("/create-product-variant")
    public ResponseEntity<BaseResponse<AddProductVariantResponse>> add(@RequestBody @Valid AddProductVariantRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        ResponseEntity<BaseResponse<AddProductVariantResponse>> validationResponse = validateProductDependencies(request.getProductId(), request.getColorId());
        if (validationResponse != null) {
            return validationResponse;
        }

        AddProductVariantResponse addProductResponse = productVariantService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addProductResponse);
    }

    @PutMapping("/update-product-variant")
    public ResponseEntity<BaseResponse<UpdateProductVariantResponse>> update(@RequestBody @Valid UpdateProductVariantRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        if(productVariantService.getById(request.getId()) == null){
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_PRODUCT_NOT_FOUND, null);
        }

        ResponseEntity<BaseResponse<UpdateProductVariantResponse>> validationResponse = validateProductDependencies(request.getProductId(), request.getColorId());
        if (validationResponse != null) {
            return validationResponse;
        }

        UpdateProductVariantResponse updateProductResponse = productVariantService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateProductResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        productVariantService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }

    private <T> ResponseEntity<BaseResponse<T>> validateProductDependencies(int productId, int colorId) {
        if (colorService.getById(colorId) == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_COLOR_NOT_FOUND, null);
        }

        if (productService.getById(productId) == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_PRODUCT_NOT_FOUND, null);
        }
        return null;
    }
}
