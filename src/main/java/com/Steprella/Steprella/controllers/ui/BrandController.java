package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.BrandService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.ListBrandResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@AllArgsConstructor
@PreAuthorize("permitAll()")
public class BrandController extends BaseController {

    private final BrandService brandService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListBrandResponse>>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<ListBrandResponse> brands;
        int totalCount;

        if (page == null || size == null) {
            brands = brandService.getAll(null, null);
            totalCount = brands.size();
        } else {
            brands = brandService.getAll(page, size);
            totalCount = brandService.getTotalCount();
        }

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, brands, totalCount);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListBrandResponse>> getById(@PathVariable int id){
        ListBrandResponse brand = brandService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, brand);
    }
}
