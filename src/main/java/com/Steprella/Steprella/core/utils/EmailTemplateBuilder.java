package com.Steprella.Steprella.core.utils;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateBuilder {

    private static final int CODE_EXPIRY_MINUTES = 2;

    public String buildVerificationEmailTemplate(String code) {
        return """
                <html>
                <head>
                    <style>
                        .email-container {
                            font-family: Arial, sans-serif;
                            margin: 20px;
                            padding: 20px;
                            border: 1px solid #ddd;
                            border-radius: 5px;
                            background-color: #f9f9f9;
                        }
                        .email-header {
                            color: #333;
                            font-size: 24px;
                            font-weight: bold;
                            text-align: center;
                            margin-bottom: 20px;
                        }
                        .email-body {
                            color: #555;
                            font-size: 16px;
                            line-height: 1.5;
                        }
                        .verification-code {
                            font-size: 20px;
                            font-weight: bold;
                            color: #d9534f;
                            text-align: center;
                            margin: 20px 0;
                        }
                        .email-footer {
                            margin-top: 20px;
                            font-size: 12px;
                            color: #777;
                            text-align: center;
                        }
                    </style>
                </head>
                <body>
                    <div class="email-container">
                        <div class="email-header">Steprella Doğrulama Kodu</div>
                        <div class="email-body">
                            Merhaba,<br><br>
                            Giriş işleminizi tamamlamak için aşağıdaki doğrulama kodunu kullanın. Bu kod, yalnızca <b>%d dakika</b> boyunca geçerlidir.
                        </div>
                        <div class="verification-code">%s</div>
                        <div class="email-body">
                            Eğer bu talebi siz yapmadıysanız, lütfen bu mesajı dikkate almayın.
                        </div>
                        <div class="email-footer">
                            © 2024 Steprella. Tüm hakları saklıdır.
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(CODE_EXPIRY_MINUTES, code);
    }

    public String buildActivationEmailTemplate(String verificationUrl) {
        return "<html><body>" +
                "<h1>Hesabınızı Aktif Hale Getirin</h1>" +
                "<p>Hesabınızı aktif hale getirmek için aşağıdaki bağlantıya tıklayın:</p>" +
                "<a href=\"" + verificationUrl + "\" style=\"display: inline-block; " +
                "padding: 10px 20px; font-size: 16px; text-decoration: none; " +
                "color: white; background-color: #4CAF50; border-radius: 5px;\">Hesabınızı Doğrulayın</a>" +
                "</body></html>";
    }
}
