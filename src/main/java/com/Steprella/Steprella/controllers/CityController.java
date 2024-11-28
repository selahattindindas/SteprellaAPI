package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.CityService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.cities.ListCityResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@AllArgsConstructor
public class CityController extends BaseController{

    private final CityService cityService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListCityResponse>>> getAll(){
        List<ListCityResponse> cities = cityService.getAll();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListCityResponse>> getById(@PathVariable int id) {
        ListCityResponse city = cityService.getById(id);

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, city);
    }
}
