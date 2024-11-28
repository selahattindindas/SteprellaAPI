package com.Steprella.Steprella.core.configurations;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.exceptions.problemdetails.BusinessProblemDetails;
import com.Steprella.Steprella.core.utils.exceptions.problemdetails.FileUploadDetails;
import com.Steprella.Steprella.core.utils.exceptions.problemdetails.InternalServerErrorDetails;
import com.Steprella.Steprella.core.utils.exceptions.problemdetails.NotFoundDetails;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.FileUploadException;
import com.Steprella.Steprella.core.utils.exceptions.types.InternalServerErrorException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler({ FileUploadException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse<FileUploadDetails>> handleRuntimeException(FileUploadException exception) {
        return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST,
                new FileUploadDetails(exception.getMessage()));
    }

    @ExceptionHandler({ BusinessException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse<BusinessProblemDetails>> handleRuntimeException(BusinessException exception) {
        return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST,
                new BusinessProblemDetails(exception.getMessage()));
    }

    @ExceptionHandler({ NotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BaseResponse<NotFoundDetails>> handleRuntimeException(NotFoundException exception) {
        return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_NOT_FOUND,
                new NotFoundDetails(exception.getMessage()));
    }

    @ExceptionHandler({ InternalServerErrorException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse<InternalServerErrorDetails>> handleInternalServerError(InternalServerErrorException exception) {
        return sendResponse(HttpStatus.INTERNAL_SERVER_ERROR, Messages.Error.CUSTOM_INTERNAL_SERVER_ERROR,
                new InternalServerErrorDetails(exception.getMessage()));
    }
}
