package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListProductResponse>> getById(@PathVariable int id) {
        ListProductResponse product = productService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, product);
    }
}
