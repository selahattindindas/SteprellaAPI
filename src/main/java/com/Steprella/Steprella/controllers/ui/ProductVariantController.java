package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product-variants")
@PreAuthorize("permitAll()")

public class ProductVariantController extends BaseController {

    private final ProductVariantService productVariantService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListProductVariantResponse>> getById(@PathVariable int id) {
        ListProductVariantResponse product = productVariantService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, product);
    }

    @GetMapping("/get-active-products")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> getActiveProductVariants(
            @RequestParam int page,
            @RequestParam int size) {

        List<ListProductVariantResponse> products = productVariantService.getActiveProductVariants(page, size);

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, products);
    }

    @GetMapping("/by-product-id/{productId}")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> getByProductId(@PathVariable int productId) {
        List<ListProductVariantResponse> productVariants = productVariantService.getByProductId(productId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, productVariants);
    }

    @GetMapping("/filter")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> filterProducts(
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer colorId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer sizeValue,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer materialId,
            @RequestParam(required = false) Integer usageAreaId,
            @RequestParam(required = false) List<Integer> featureIds,
            @RequestParam int page,
            @RequestParam int size) {

        List<ListProductVariantResponse> filteredProducts = productVariantService.filterProducts(
                brandId, colorId, categoryId, sizeValue,
                minPrice, maxPrice, materialId, usageAreaId, featureIds,
                page, size);
        int totalCount = productVariantService.getTotalCount();

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, filteredProducts, totalCount);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> searchProductVariants(
            @RequestParam String searchTerm,
            @RequestParam int page,
            @RequestParam int size) {

        List<ListProductVariantResponse> searchedProducts = productVariantService.searchProductVariants(searchTerm, page, size);
        int totalCount = productVariantService.getTotalCount();

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, searchedProducts, totalCount);
    }
}
