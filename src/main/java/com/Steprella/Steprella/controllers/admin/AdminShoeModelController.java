package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ShoeModelService;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.AddShoeModelRequest;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.UpdateShoeModelRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.AddShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.UpdateShoeModelResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-shoe-models")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminShoeModelController extends BaseController {

    private final ShoeModelService shoeModelService;

    @PostMapping("/create-shoe-model")
    public ResponseEntity<BaseResponse<AddShoeModelResponse>> add(@RequestBody @Valid AddShoeModelRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddShoeModelResponse addModelResponse = shoeModelService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addModelResponse);
    }

    @PutMapping("/update-shoe-model")
    public ResponseEntity<BaseResponse<UpdateShoeModelResponse>> update(@RequestBody @Valid UpdateShoeModelRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        UpdateShoeModelResponse updateModelResponse = shoeModelService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateModelResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        shoeModelService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
