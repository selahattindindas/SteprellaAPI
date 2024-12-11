package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.EmailTemplateBuilder;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.VerificationCodeService;
import com.Steprella.Steprella.services.dtos.responses.verifications.VerificationCodeResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Value("${spring.mail.username}")
    private String mailName;

    private final JavaMailSender mailSender;
    private final EmailTemplateBuilder emailTemplateBuilder;

    private static final int CODE_LENGTH = 6;
    private static final int CODE_EXPIRY_MINUTES = 2;

    private final Map<String, VerificationCodeResponse> verificationCodes = new HashMap<>();


    @Override
    public void sendVerificationCode(String email) {
        String code = generateCode();
        String subject = Messages.Info.VERIFICATION_CODE_TITLE;
        String body = emailTemplateBuilder.buildVerificationEmailTemplate(code);

        sendHtmlEmail(email, subject, body);

        verificationCodes.put(email, new VerificationCodeResponse(code, System.currentTimeMillis()));
    }

    @Override
    public boolean isValidCode(String email, String inputCode) {
        VerificationCodeResponse storedCode = verificationCodes.get(email);

        if (storedCode == null || isCodeExpired(storedCode)) {
            verificationCodes.remove(email);
            return false;
        }

        if (storedCode.getCode().equals(inputCode)) {
            verificationCodes.remove(email);
            return true;
        }

        return false;
    }

    @Override
    public boolean verifyCode(String email, String inputCode) {
        VerificationCodeResponse storedCode = verificationCodes.get(email);
        return storedCode != null && storedCode.getCode().equals(inputCode);
    }

    @Override
    public void sendActivationEmail(String email) {
        String code = generateCode();
        String verificationUrl = buildVerificationUrl(email, code);

        String subject = Messages.Info.ACTIVATION_EMAIL_TITLE;
        String body = emailTemplateBuilder.buildActivationEmailTemplate(verificationUrl);

        sendHtmlEmail(email, subject, body);
        verificationCodes.put(email, new VerificationCodeResponse(code));
    }

    private String buildVerificationUrl(String email, String code) {
        String baseUrl = "http://localhost:8080/api/verification-codes/verify";
        return baseUrl + "?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) + "&code=" + code;
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom(mailName);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BusinessException(Messages.Error.EMAIL_FILE_UPLOAD_FAILED);
        }
    }

    private String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    private boolean isCodeExpired(VerificationCodeResponse storedCode) {
        return System.currentTimeMillis() - storedCode.getTimestamp() > CODE_EXPIRY_MINUTES * 60 * 1000;
    }
}

