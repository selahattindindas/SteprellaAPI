package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.dtos.requests.products.ProductSearchCriteria;
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

    @GetMapping("/by-product-id/{productId}")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> getByProductId(@PathVariable int productId) {
        List<ListProductVariantResponse> productVariants = productVariantService.getByProductId(productId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, productVariants);
    }

    @GetMapping("/random-product")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> getRandomVariants(
            @RequestParam(defaultValue = "10") int count) {
        List<ListProductVariantResponse> productVariants = productVariantService.getRandomVariants(count);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, productVariants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListProductVariantResponse>> getById(@PathVariable int id) {
        ListProductVariantResponse product = productVariantService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, product);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<ListProductVariantResponse>>> search(
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer materialId,
            @RequestParam(required = false) Integer usageAreaId,
            @RequestParam(required = false) Integer colorId,
            @RequestParam(required = false) Integer sizeValue,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) List<Integer> featureIds,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ProductSearchCriteria criteria = ProductSearchCriteria.builder()
                .brandId(brandId)
                .categoryId(categoryId)
                .materialId(materialId)
                .usageAreaId(usageAreaId)
                .colorId(colorId)
                .sizeValue(sizeValue)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .featureIds(featureIds)
                .searchTerm(searchTerm)
                .page(page)
                .size(size)
                .build();

        List<ListProductVariantResponse> productVariants = productVariantService.search(criteria);
        int totalCount = productVariantService.getTotalCount(criteria);

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, productVariants, totalCount);
    }
}
