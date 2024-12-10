package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.AuthService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.abstracts.VerificationCodeService;
import com.Steprella.Steprella.services.dtos.requests.tokens.RefreshTokenRequest;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.tokens.RefreshTokenResponse;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.LoginUserResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController extends BaseController {

    private final AuthService authService;
    private final VerificationCodeService verificationCodeService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AddUserResponse>> register(@RequestBody @Valid AddUserRequest request) {
        AddUserResponse registerResponse = authService.register(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginUserResponse>> login(@RequestBody @Valid LoginUserRequest request, HttpSession session) {
        LoginUserResponse loginResponse = authService.login(request);
        session.setAttribute("isVerified", false);

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, loginResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<RefreshTokenResponse>> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        RefreshTokenResponse refreshTokenResponse = authService.refreshToken(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, refreshTokenResponse);
    }

    @PostMapping("/send-code")
    public ResponseEntity<BaseResponse<String>> sendVerificationCode(@RequestParam @Email String email) {
        verificationCodeService.sendVerificationCode(email);
        return sendResponse(HttpStatus.OK, Messages.Info.VERIFICATION_CODE_SENT, null);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<BaseResponse<String>> verifyCode(
            @RequestParam @Email String email, @RequestParam String code, HttpSession session) {
        boolean valid = verificationCodeService.isValidCode(email, code);
        if (valid) {
            session.setAttribute("isVerified", true);
            return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
        } else {
            return sendResponse(HttpStatus.UNAUTHORIZED, Messages.Error.VERIFICATION_CODE_INVALID, null);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<BaseResponse<String>> verifyAccount(@RequestParam String email, @RequestParam String code) {
        if (verificationCodeService.verifyCode(email, code)) {
            userService.setVerified(email);
            return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
        } else {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.INVALID_VERIFICATION_CODE, null);
        }
    }
}
