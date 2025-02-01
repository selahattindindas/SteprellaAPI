package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.UsageAreaService;
import com.Steprella.Steprella.services.dtos.requests.usageareas.AddUsageAreaRequest;
import com.Steprella.Steprella.services.dtos.requests.usageareas.UpdateUsageAreaRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.usageareas.AddUsageAreaResponse;
import com.Steprella.Steprella.services.dtos.responses.usageareas.UpdateUsageAreaResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-usage-areas")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminUsageAreaController extends BaseController {

    private final UsageAreaService usageAreaService;

    @PostMapping("/create-usage-area")
    public ResponseEntity<BaseResponse<AddUsageAreaResponse>> add(@RequestBody @Valid AddUsageAreaRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddUsageAreaResponse response = usageAreaService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, response);
    }

    @PutMapping("/update-usage-area")
    public ResponseEntity<BaseResponse<UpdateUsageAreaResponse>> update(@RequestBody @Valid UpdateUsageAreaRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        UpdateUsageAreaResponse response = usageAreaService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        usageAreaService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
