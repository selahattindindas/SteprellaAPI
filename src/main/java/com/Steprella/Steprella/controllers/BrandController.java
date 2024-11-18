package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.BrandService;
import com.Steprella.Steprella.services.dtos.requests.brands.AddBrandRequest;
import com.Steprella.Steprella.services.dtos.requests.brands.UpdateBrandRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.AddBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.ListBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.UpdateBrandResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController extends BaseController{

    private final BrandService brandService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListBrandResponse>>> getAll(){
        List<ListBrandResponse> brands = brandService.getAll();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListBrandResponse>> getById(@PathVariable int id){
        ListBrandResponse brand = brandService.getById(id);
        if(brand == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_BRAND_NOT_FOUND, null);
        else
            return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, brand);
    }

    @PostMapping("/create-brand")
    public ResponseEntity<BaseResponse<AddBrandResponse>> add(@RequestBody @Valid AddBrandRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddBrandResponse addBrandResponse = brandService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addBrandResponse);
    }

    @PutMapping("/update-brand")
    public ResponseEntity<BaseResponse<UpdateBrandResponse>> update(@RequestBody @Valid UpdateBrandRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        if(brandService.getById(request.getId()) == null){
                return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_BRAND_NOT_FOUND, null);
        }

        UpdateBrandResponse updateBrandResponse = brandService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateBrandResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        brandService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
