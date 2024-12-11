package com.Steprella.Steprella.services.dtos.responses.verifications;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationCodeResponse {
    String code;
    Long timestamp;

    public VerificationCodeResponse(String code) {
        this.code = code;
        this.timestamp = null;
    }

    public VerificationCodeResponse(String code, Long timestamp) {
        this.code = code;
        this.timestamp = timestamp;
    }
}