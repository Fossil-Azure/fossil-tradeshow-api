package com.fossil.tradeshow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendOrderConfirmationEmail(String toEmail, String subject, Map<String, Object> variables) throws JsonProcessingException {
        try {
            // Convert Order Data to JSON for Debugging
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(variables);
            System.out.println("Email Data:\n" + jsonString);

            // Populate Thymeleaf template
            Context context = new Context();
            context.setVariables(variables);
            String emailContent = templateEngine.process("order-confirmation", context);

            // Create the email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(new InternetAddress("22423.shivam@gmail.com", "Fossil Group"));
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
