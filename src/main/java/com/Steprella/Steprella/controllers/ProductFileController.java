package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ProductFileService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.dtos.requests.productfiles.AddProductFileRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.AddProductFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.ListProductFileResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product-files")
@AllArgsConstructor
public class ProductFileController extends BaseController{

    private ProductFileService productFileService;
    private ProductVariantService productVariantService;

    @GetMapping("/by-product-variant-id/{productVariantId}")
    public ResponseEntity<BaseResponse<ListProductFileResponse>> getByRoomTypeId(@PathVariable("productVariantId") int productVariantId){
        ListProductFileResponse fileResponse = productFileService.getByProductVariantId(productVariantId);
        if (fileResponse == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_PRODUCT_NOT_FOUND, null);
        } else {
            return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, fileResponse);
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<AddProductFileResponse>> add(@RequestParam("productVariantId") int productVariantId,
                                                                    @RequestParam("file") List<MultipartFile> files) throws IOException {

        if (productVariantService.getById(productVariantId) == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_PRODUCT_NOT_FOUND, null);
        }

        AddProductFileRequest request = new AddProductFileRequest();
        request.setProductVariantId(productVariantId);
        request.setFiles(files);

        AddProductFileResponse response = productFileService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        productFileService.delete(id);
        return sendResponse(HttpStatus.NO_CONTENT, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}