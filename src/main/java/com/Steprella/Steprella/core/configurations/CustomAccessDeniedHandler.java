package com.Steprella.Steprella.core.configurations;
import com.Steprella.Steprella.core.utils.messages.Messages;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler  {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException , ServletException {

        String errorMessage = Messages.Error.ACCESS_DENIED;

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        String jsonResponse = "{\"message\": \"" + errorMessage + "\"}";

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
