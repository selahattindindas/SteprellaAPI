package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductService;
import com.Steprella.Steprella.services.dtos.requests.products.ProductSearchCriteria;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
@PreAuthorize("permitAll()")
public class ProductController extends BaseController {

    private final ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListProductResponse>>> getAll(
            @RequestParam int page,
            @RequestParam int size) {

        List<ListProductResponse> products = productService.getAll(page, size);
        int totalCount = productService.getTotalCount();

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, products, totalCount);
    }

    @GetMapping("/random-product")
    public ResponseEntity<BaseResponse<List<ListProductResponse>>> getRandomVariants(
            @RequestParam(defaultValue = "10") int count) {
        List<ListProductResponse> products = productService.getRandomVariants(count);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, products);
    }

    @GetMapping("/filter")
    public ResponseEntity<BaseResponse<List<ListProductResponse>>> filter(
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer materialId,
            @RequestParam(required = false) Integer usageAreaId,
            @RequestParam(required = false) Integer colorId,
            @RequestParam(required = false) Integer sizeValue,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) List<Integer> featureIds,
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
                .page(page)
                .size(size)
                .build();

        List<ListProductResponse> products = productService.filter(criteria);
        int totalCount = productService.getTotalCount(criteria);

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, products, totalCount);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<ListProductResponse>>> search(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ProductSearchCriteria criteria = ProductSearchCriteria.builder()
                .searchTerm(searchTerm)
                .page(page)
                .size(size)
                .build();

        List<ListProductResponse> products = productService.filter(criteria);
        int totalCount = productService.getTotalCount(criteria);

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, products, totalCount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListProductResponse>> getById(@PathVariable int id) {
        ListProductResponse product = productService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, product);
    }
}
