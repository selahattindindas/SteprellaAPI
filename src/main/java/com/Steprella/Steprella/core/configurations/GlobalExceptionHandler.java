package com.Steprella.Steprella.core.configurations;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.exceptions.problemdetails.FileUploadDetails;
import com.Steprella.Steprella.core.utils.exceptions.types.FileUploadException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler({ FileUploadException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse<FileUploadDetails>> handleRuntimeException(FileUploadException exception) {
        return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST,
                new FileUploadDetails(exception.getMessage()));
    }
}
