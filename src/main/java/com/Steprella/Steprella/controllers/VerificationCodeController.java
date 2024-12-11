package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.abstracts.VerificationCodeService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verification-codes")
@AllArgsConstructor
public class VerificationCodeController extends BaseController {

    private final VerificationCodeService verificationCodeService;
    private final UserService userService;

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
