package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.AuthService;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.users.LoginUserResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-auth")
public class AdminAuthController extends BaseController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginUserResponse>> adminLogin(@RequestBody @Valid LoginUserRequest request, HttpServletResponse response, HttpSession session) {
        LoginUserResponse loginResponse = authService.adminLogin(request, response);
        session.setAttribute("isVerified", false);
        session.setAttribute("userEmail", request.getEmail());

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, loginResponse);
    }
}
