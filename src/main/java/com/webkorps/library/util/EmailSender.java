package com.webkorps.library.util;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public static boolean sendEmail(String email, String name, String memberId, String userPass) {

        boolean flag = false;
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");

        String username = "anshul.g@webkorps.com";
        String password = "blocgpvteeuyaqtc";
        String from = "anshul.g@webkorps.com";
        String text = getMessageText(name, memberId, userPass);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Confirmation Email From WebKorps");
            message.setContent(text, "text/html");

            Transport.send(message);
            flag = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static String getMessageText(String name, String memberId, String userPass) {
        String text = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: 'Arial', sans-serif;\n"
                + "            margin: 0;\n"
                + "            padding: 0;\n"
                + "            background-color: #f7f9fc;\n"
                + "        }\n"
                + "        .container {\n"
                + "            width: 100%;\n"
                + "            max-width: 600px;\n"
                + "            margin: 30px auto;\n"
                + "            background: white;\n"
                + "            border-radius: 8px;\n"
                + "            box-shadow: 0 0 10px rgba(0,0,0,0.1);\n"
                + "            overflow: hidden;\n"
                + "        }\n"
                + "        .header {\n"
                + "            background: #007BFF;\n"
                + "            color: white;\n"
                + "            padding: 20px;\n"
                + "            text-align: center;\n"
                + "        }\n"
                + "        .content {\n"
                + "            padding: 20px;\n"
                + "            line-height: 1.6;\n"
                + "            color: #333;\n"
                + "        }\n"
                + "        .button {\n"
                + "            display: inline-block;\n"
                + "            padding: 12px 20px;\n"
                + "            margin-top: 20px;\n"
                + "            background: #28a745;\n"
                + "            color: white;\n"
                + "            text-decoration: none;\n"
                + "            border-radius: 5px;\n"
                + "            font-weight: bold;\n"
                + "        }\n"
                + "        .footer {\n"
                + "            text-align: center;\n"
                + "            font-size: 12px;\n"
                + "            color: #777;\n"
                + "            padding: 15px;\n"
                + "            background: #f1f1f1;\n"
                + "        }\n"
                + "        @media (max-width: 600px) {\n"
                + "            .container {\n"
                + "                width: 100%;\n"
                + "                margin: 0;\n"
                + "            }\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "            <h2>Welcome to Our Service!</h2>\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <p>Dear <strong>" + name + "</strong>,</p>\n"
                + "            <p>Thank you for signing up! Your account has been created successfully. Here are your credentials:</p>\n"
                + "            <p><strong>Username:</strong> " + memberId + "<br>\n"
                + "            <strong>Password:</strong> " + userPass + "</p>\n"
                + "            <p>To get started, please click the button below to log in:</p>\n"
                + "            <a href=\"http://localhost:8081/LibraryManagement/login.jsp\" class=\"button\">Login to Your Account</a>\n"
                + "        </div>\n"
                + "        <div class=\"footer\">\n"
                + "            <p>If you have any questions, feel free to <a href=\"mailto:support@example.com\">contact us</a>.</p>\n"
                + "            <p>&copy; 2024 Your Company Name. All rights reserved.</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";

        return text;
    }
}
