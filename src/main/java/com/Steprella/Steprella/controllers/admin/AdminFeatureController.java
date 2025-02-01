package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.FeatureService;
import com.Steprella.Steprella.services.dtos.requests.features.AddFeatureRequest;
import com.Steprella.Steprella.services.dtos.requests.features.UpdateFeatureRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.features.AddFeatureResponse;
import com.Steprella.Steprella.services.dtos.responses.features.UpdateFeatureResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-features")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminFeatureController extends BaseController {

    private final FeatureService featureService;

    @PostMapping("/create-feature")
    public ResponseEntity<BaseResponse<AddFeatureResponse>> add(@RequestBody @Valid AddFeatureRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddFeatureResponse response = featureService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, response);
    }

    @PutMapping("/update-feature")
    public ResponseEntity<BaseResponse<UpdateFeatureResponse>> update(@RequestBody @Valid UpdateFeatureRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        UpdateFeatureResponse response = featureService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        featureService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
