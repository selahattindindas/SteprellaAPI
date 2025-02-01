package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.FeatureService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.features.ListFeatureResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/features")
@PreAuthorize("permitAll()")
public class FeatureController extends BaseController {

    private final FeatureService featureService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListFeatureResponse>>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<ListFeatureResponse> features;
        int totalCount;

        if (page == null || size == null) {
            features = featureService.getAll(null, null);
            totalCount = features.size();
        } else {
            features = featureService.getAll(page, size);
            totalCount = featureService.getTotalCount();
        }

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, features, totalCount);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListFeatureResponse>> getById(@PathVariable int id){
        ListFeatureResponse feature = featureService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, feature);
    }
}
