package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.EmailTemplateBuilder;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.VerificationCodeService;
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
    private final Map<String, CodeData> verificationCodes = new HashMap<>();


    public String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public void sendVerificationCode(String email) {
        String code = generateCode();
        String subject = Messages.Info.VERIFICATION_CODE_TITLE;

        String body = emailTemplateBuilder.buildVerificationEmailTemplate(code);

        sendHtmlEmail(email, subject, body);

        verificationCodes.put(email, new CodeData(code, System.currentTimeMillis()));
    }

    public boolean isValidCode(String email, String inputCode) {
        CodeData storedCode = verificationCodes.get(email);

        if (storedCode == null || System.currentTimeMillis() - storedCode.timestamp > CODE_EXPIRY_MINUTES * 60 * 1000) {
            verificationCodes.remove(email);
            return false;
        }

        if (storedCode.code.equals(inputCode)) {
            verificationCodes.remove(email);
            return true;
        }

        return false;
    }

    public boolean verifyCode(String email, String inputCode) {
        CodeData storedCode = verificationCodes.get(email);

        if (storedCode == null) {
            return false;
        }

        return storedCode.code.equals(inputCode);
    }

    public void sendActivationEmail(String email) {
        String code = generateCode();
        String baseUrl = "http://localhost:8080/api/auth/verify";
        String verificationUrl = baseUrl + "?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) + "&code=" + code;

        String subject = Messages.Info.ACTIVATION_EMAIL_TITLE;
        String body = emailTemplateBuilder.buildActivationEmailTemplate(verificationUrl);

        sendHtmlEmail(email, subject, body);
        verificationCodes.put(email, new CodeData(code));
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

    private static class CodeData {
        String code;
        Long timestamp;

        public CodeData(String code) {
            this.code = code;
            this.timestamp = null;
        }

        public CodeData(String code, Long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
    }

}

