package com.Steprella.Steprella.interceptors;

import com.Steprella.Steprella.core.utils.messages.Messages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class VerificationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            if (session == null || session.getAttribute("isVerified") == null || !(Boolean) session.getAttribute("isVerified")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(Messages.Error.EMAIL_VERIFICATION_REQUIRED);
                return false;
            }
        }
        return true;
    }
}
