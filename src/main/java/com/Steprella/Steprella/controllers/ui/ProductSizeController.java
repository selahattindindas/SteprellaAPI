package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductSizeService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product-sizes")
@PreAuthorize("permitAll()")
public class ProductSizeController extends BaseController {

    private final ProductSizeService productSizeService;

    @GetMapping("/by-product-variant-id/{productVariantId}")
    public ResponseEntity<BaseResponse<List<ListProductSizeResponse>>> getProductSizesByProductVariantId(@PathVariable int productVariantId){
        List<ListProductSizeResponse> productSizes = productSizeService.getProductSizesByProductVariantId(productVariantId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, productSizes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListProductSizeResponse>> getById(@PathVariable int id){
        ListProductSizeResponse productSize = productSizeService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, productSize);
    }
}
