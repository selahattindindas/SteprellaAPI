package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    protected<T> ResponseEntity<BaseResponse<T>> sendResponse(HttpStatus statusCode, String statusMessage, T result) {
        BaseResponse<T> responseModels = new BaseResponse<>();
        responseModels.setStatusCode(statusCode.value());
        responseModels.setStatusMessage(statusMessage);
        responseModels.setResult(result);

        return ResponseEntity.status(statusCode).body(responseModels);
    }
}
