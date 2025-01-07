package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ShoeModelService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.ListShoeModelResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/shoe-models")
@PreAuthorize("permitAll()")
public class ShoeModelController extends BaseController {

    private final ShoeModelService shoeModelService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListShoeModelResponse>>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<ListShoeModelResponse> models;
        int totalCount;

        if (page == null || size == null) {
            models = shoeModelService.getAll(null, null);
            totalCount = models.size();
        } else {
            models = shoeModelService.getAll(page, size);
            totalCount = shoeModelService.getTotalCount();
        }

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, models, totalCount);
    }

    @GetMapping("/by-brand-id/{brandId}")
    public ResponseEntity<BaseResponse<List<ListShoeModelResponse>>> getShoeModelByBrandId(
            @PathVariable int brandId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<ListShoeModelResponse> models;
        int totalCount;

        if (page == null || size == null) {
            models = shoeModelService.getShoeModelsByBrandId(brandId,null, null);
            totalCount = models.size();
        } else {
            models = shoeModelService.getShoeModelsByBrandId(brandId, page, size);
            totalCount = shoeModelService.getTotalCount();
        }

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, models, totalCount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListShoeModelResponse>> getById(@PathVariable int id){
        ListShoeModelResponse model = shoeModelService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, model);
    }
}
