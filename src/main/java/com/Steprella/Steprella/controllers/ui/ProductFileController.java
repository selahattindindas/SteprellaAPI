package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductFileService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.ListProductFileResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product-files")
@PreAuthorize("permitAll()")
public class ProductFileController extends BaseController {

    private final ProductFileService productFileService;

    @GetMapping("/by-product-variant-id/{productVariantId}")
    public ResponseEntity<BaseResponse<ListProductFileResponse>> getByProductVariantId(@PathVariable("productVariantId") int productVariantId){
        ListProductFileResponse fileResponse = productFileService.getByProductVariantId(productVariantId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, fileResponse);
    }
}
