package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.MaterialService;
import com.Steprella.Steprella.services.dtos.requests.materials.AddMaterialRequest;
import com.Steprella.Steprella.services.dtos.requests.materials.UpdateMaterialRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.materials.AddMaterialResponse;
import com.Steprella.Steprella.services.dtos.responses.materials.UpdateMaterialResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-materials")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminMaterialController extends BaseController {

    private final MaterialService materialService;

    @PostMapping("/create-material")
    public ResponseEntity<BaseResponse<AddMaterialResponse>> add(@RequestBody @Valid AddMaterialRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddMaterialResponse response = materialService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, response);
    }

    @PutMapping("/update-material")
    public ResponseEntity<BaseResponse<UpdateMaterialResponse>> update(@RequestBody @Valid UpdateMaterialRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        UpdateMaterialResponse response = materialService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        materialService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
