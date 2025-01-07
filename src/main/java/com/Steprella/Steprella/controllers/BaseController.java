package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    protected <T> ResponseEntity<BaseResponse<T>> sendResponse(
            HttpStatus statusCode,
            String statusMessage,
            T data) {
        return sendResponse(statusCode, statusMessage, data, null);
    }

    protected <T> ResponseEntity<BaseResponse<T>> sendResponse(
            HttpStatus statusCode,
            String statusMessage,
            T data,
            Integer totalCount) {
        BaseResponse<T> responseModels = new BaseResponse<>();
        responseModels.setStatusCode(statusCode.value());
        responseModels.setStatusMessage(statusMessage);
        responseModels.setData(data);

        if (totalCount != null) {
            responseModels.setTotalCount(totalCount);
        }

        return ResponseEntity.status(statusCode).body(responseModels);
    }
}
