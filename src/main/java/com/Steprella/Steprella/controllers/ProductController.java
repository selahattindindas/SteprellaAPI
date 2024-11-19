package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.BrandService;
import com.Steprella.Steprella.services.abstracts.CategoryService;
import com.Steprella.Steprella.services.abstracts.ProductService;
import com.Steprella.Steprella.services.abstracts.ShoeModelService;
import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController extends BaseController{

    private final ProductService productService;
    private final ShoeModelService shoeModelService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListProductResponse>>> getAll(){
        List<ListProductResponse> products = productService.getAll();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListProductResponse>> getById(@PathVariable int id) {
        ListProductResponse product = productService.getById(id);
        if (product == null){
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_PRODUCT_NOT_FOUND, null);
        }
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, product);
    }

    @PostMapping("/create-product")
    public ResponseEntity<BaseResponse<AddProductResponse>> add(@RequestBody @Valid AddProductRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }
        ResponseEntity<BaseResponse<AddProductResponse>> validationResponse = validateProductDependencies(request.getShoeModelId(), request.getCategoryId(), request.getBrandId());
        if (validationResponse != null) {
            return validationResponse;
        }

        AddProductResponse addProductResponse = productService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addProductResponse);
    }

    @PutMapping("/update-product")
    public ResponseEntity<BaseResponse<UpdateProductResponse>> update(@RequestBody @Valid UpdateProductRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        ResponseEntity<BaseResponse<UpdateProductResponse>> validationResponse = validateProductDependencies(request.getShoeModelId(), request.getCategoryId(), request.getBrandId());
        if (validationResponse != null) {
            return validationResponse;
        }

        UpdateProductResponse updateProductResponse = productService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateProductResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        productService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }

    private <T> ResponseEntity<BaseResponse<T>> validateProductDependencies(int shoeModelId, int categoryId, int brandId) {
        if (shoeModelService.getById(shoeModelId) == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_SHOE_MODEL_NOT_FOUND, null);
        }
        if (categoryService.getById(categoryId) == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_CATEGORY_NOT_FOUND, null);
        }
        if (brandService.getById(brandId) == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_BRAND_NOT_FOUND, null);
        }
        return null;
    }
}
