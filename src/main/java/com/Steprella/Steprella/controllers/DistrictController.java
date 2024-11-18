package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.CityService;
import com.Steprella.Steprella.services.abstracts.DistrictService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.district.ListDistrictResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class DistrictController extends BaseController{

    private final DistrictService districtService;
    private final CityService cityService;

    @GetMapping("/by-city-id/{cityId}")
    public ResponseEntity<BaseResponse<List<ListDistrictResponse>>> getResponseByRoomTypeId(@PathVariable int cityId) {
        if (cityService.getById(cityId) == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_CITY_NOT_FOUND, null);
        }

        List<ListDistrictResponse> districts = districtService.getDistrictsByCityId(cityId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, districts);
    }
}
