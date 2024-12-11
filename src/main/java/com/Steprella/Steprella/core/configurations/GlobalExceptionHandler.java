package com.Steprella.Steprella.core.configurations;

import com.Steprella.Steprella.core.utils.exceptions.problemdetails.*;
import com.Steprella.Steprella.core.utils.exceptions.types.*;
import com.Steprella.Steprella.core.utils.messages.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ FileUploadException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FileUploadDetails handleFileUploadException(FileUploadException exception) {
        return new FileUploadDetails(exception.getMessage());
    }

    @ExceptionHandler({ BusinessException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BusinessProblemDetails handleBusinessException(BusinessException exception) {
        return new BusinessProblemDetails(exception.getMessage());
    }

    @ExceptionHandler({ NotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundDetails handleNotFoundException(NotFoundException exception) {
        return new NotFoundDetails(exception.getMessage());
    }

    @ExceptionHandler({ InternalServerErrorException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public InternalServerErrorDetails handleInternalServerError(InternalServerErrorException exception) {
        return new InternalServerErrorDetails(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ForbiddenDetails handleAccessDeniedException(AccessDeniedException exception) {
        return new ForbiddenDetails(Messages.Error.ACCESS_DENIED);
    }
}
