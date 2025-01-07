package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.DistrictService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.district.ListDistrictResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/districts")
@PreAuthorize("permitAll()")
public class DistrictController extends BaseController {

    private final DistrictService districtService;

    @GetMapping("/by-city-id/{cityId}")
    public ResponseEntity<BaseResponse<List<ListDistrictResponse>>> getResponseByCityId(@PathVariable int cityId) {
        List<ListDistrictResponse> districts = districtService.getDistrictsByCityId(cityId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, districts);
    }
}
