package com.Steprella.Steprella.services.abstracts;

public interface VerificationCodeService {

    void sendVerificationCode(String email);

    boolean isValidCode(String email, String inputCode);

    void sendActivationEmail(String email);

    boolean verifyCode(String email, String inputCode);
}
