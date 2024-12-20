package com.Steprella.Steprella.controllers;

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
@RequestMapping("/api/districts")
@AllArgsConstructor
public class DistrictController extends BaseController{

    private final DistrictService districtService;

    @GetMapping("/by-city-id/{cityId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<List<ListDistrictResponse>>> getResponseByCityId(@PathVariable int cityId) {
        List<ListDistrictResponse> districts = districtService.getDistrictsByCityId(cityId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, districts);
    }
}
