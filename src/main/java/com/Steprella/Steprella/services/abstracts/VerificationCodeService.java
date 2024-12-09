package com.Steprella.Steprella.services.abstracts;

public interface VerificationCodeService {

    String generateCode();

    void sendVerificationCode(String email);

    boolean isValidCode(String email, String inputCode);
}
