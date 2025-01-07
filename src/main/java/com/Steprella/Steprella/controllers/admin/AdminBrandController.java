package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.BrandService;
import com.Steprella.Steprella.services.dtos.requests.brands.AddBrandRequest;
import com.Steprella.Steprella.services.dtos.requests.brands.UpdateBrandRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.AddBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.UpdateBrandResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-brands")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminBrandController extends BaseController {

    private final BrandService brandService;

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

        UpdateBrandResponse updateBrandResponse = brandService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateBrandResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        brandService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
