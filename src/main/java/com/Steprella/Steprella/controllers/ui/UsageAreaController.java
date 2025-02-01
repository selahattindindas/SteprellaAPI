package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.UsageAreaService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.usageareas.ListUsageAreaResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usage-areas")
@PreAuthorize("permitAll()")
public class UsageAreaController extends BaseController {

    private final UsageAreaService usageAreaService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListUsageAreaResponse>>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<ListUsageAreaResponse> usageAreas;
        int totalCount;

        if (page == null || size == null) {
            usageAreas = usageAreaService.getAll(null, null);
            totalCount = usageAreas.size();
        } else {
            usageAreas = usageAreaService.getAll(page, size);
            totalCount = usageAreaService.getTotalCount();
        }

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, usageAreas, totalCount);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListUsageAreaResponse>> getById(@PathVariable int id){
        ListUsageAreaResponse usageArea = usageAreaService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, usageArea);
    }
}
