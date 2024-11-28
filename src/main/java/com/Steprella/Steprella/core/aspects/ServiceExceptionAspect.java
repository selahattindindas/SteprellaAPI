package com.Steprella.Steprella.core.aspects;

import com.Steprella.Steprella.core.utils.exceptions.types.InternalServerErrorException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceExceptionAspect {

    @AfterThrowing(pointcut = "execution(* com.Steprella.Steprella.services.concretes.*.*(..))", throwing = "exception")
    public void handleServiceException(JoinPoint joinPoint, Throwable exception) {
        if (isServerError(exception)) {
            throw new InternalServerErrorException(Messages.Error.CUSTOM_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isServerError(Throwable exception) {
        return exception instanceof NullPointerException ||
                exception instanceof IllegalStateException;
    }
}
