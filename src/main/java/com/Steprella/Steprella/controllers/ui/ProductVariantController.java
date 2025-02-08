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

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListProductVariantResponse>> getById(@PathVariable int id) {
        ListProductVariantResponse product = productVariantService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, product);
    }
}
