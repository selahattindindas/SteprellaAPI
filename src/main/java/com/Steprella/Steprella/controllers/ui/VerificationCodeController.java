package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.abstracts.VerificationCodeService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/verification-codes")
@PreAuthorize("permitAll()")
public class VerificationCodeController extends BaseController {

    private final VerificationCodeService verificationCodeService;
    private final UserService userService;

    @PostMapping("/send-code")
    public ResponseEntity<BaseResponse<String>> sendVerificationCode(@RequestParam String email) {
        verificationCodeService.sendVerificationCode(email);
        return sendResponse(HttpStatus.OK, Messages.Info.VERIFICATION_CODE_SENT, null);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<BaseResponse<String>> verifyCode(
            @RequestParam String code,
            @RequestParam String email) {
        if (verificationCodeService.isValidCode(email, code)) {
            userService.setVerified(email);
            return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
        }
        return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.INVALID_VERIFICATION_CODE, null);
    }

    @PostMapping("/resend-code")
    public ResponseEntity<BaseResponse<String>> resendCode(@RequestParam String email) {
        verificationCodeService.sendVerificationCode(email);
        return sendResponse(HttpStatus.OK, Messages.Info.VERIFICATION_CODE_SENT, null);
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
