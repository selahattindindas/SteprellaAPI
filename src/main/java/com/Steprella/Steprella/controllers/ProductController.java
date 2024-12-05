package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductService;
import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController extends BaseController{

    private final ProductService productService;

    @GetMapping("/get-all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<List<ListProductResponse>>> getAll(){
        List<ListProductResponse> products = productService.getAll();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, products);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<ListProductResponse>> getById(@PathVariable int id) {
        ListProductResponse product = productService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, product);
    }

    @PostMapping("/create-product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<AddProductResponse>> add(@RequestBody @Valid AddProductRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        AddProductResponse addProductResponse = productService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addProductResponse);
    }

    @PutMapping("/update-product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<UpdateProductResponse>> update(@RequestBody @Valid UpdateProductRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        UpdateProductResponse updateProductResponse = productService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateProductResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        productService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
