package com.fossil.tradeshow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fossil.tradeshow.model.OrderChange;
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
import java.util.List;
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

    public void sendOrderUpdateEmail(String to, List<OrderChange> changes) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Generate dynamic email content
        String emailContent = generateEmailHtml(changes);

        helper.setTo(to);
        helper.setSubject("Your Order Has Been Updated!");
        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    private String generateEmailHtml(List<OrderChange> changes) {
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<!DOCTYPE html><html><head><style>");
        emailContent.append("body { font-family: Arial, sans-serif; background-color: #f8f9fa; padding: 20px; }");
        emailContent.append(".container { max-width: 600px; background-color: white; padding: 20px; border-radius: 8px; margin: auto; }");
        emailContent.append(".header { background-color: #0073e6; color: white; text-align: center; padding: 10px; font-size: 20px; }");
        emailContent.append(".product-table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
        emailContent.append(".product-table th, .product-table td { border: 1px solid #ddd; padding: 10px; text-align: left; }");
        emailContent.append(".increase { color: green; font-weight: bold; }");
        emailContent.append(".decrease { color: red; font-weight: bold; }");
        emailContent.append(".unchanged { color: gray; font-weight: bold; }");
        emailContent.append(".removed { color: red; font-weight: bold; text-decoration: line-through; }");
        emailContent.append("</style></head><body>");

        emailContent.append("<div class='container'><div class='header'>Order Update Notification</div>");
        emailContent.append("<p>Hello</p>");
        emailContent.append("<p>Your order has been modified. Below are the changes:</p>");

        emailContent.append("<table class='product-table'><tr><th>Image</th><th>Product</th><th>SKU</th><th>Old Quantity</th><th>New Quantity</th><th>Change</th></tr>");

        for (OrderChange change : changes) {
            emailContent.append("<tr>");
            emailContent.append("<td><img src='").append(change.getImageUrl()).append("' width='60'></td>");
            emailContent.append("<td>").append(change.getProduct()).append("</td>");
            emailContent.append("<td>").append(change.getSku()).append("</td>");

            if ("Removed".equals(change.getChange())) {
                emailContent.append("<td class='removed' colspan='2'>").append(change.getOldQuantity()).append(" (Removed)</td>");
            } else if ("Unchanged".equals(change.getChange())) {
                emailContent.append("<td colspan='2' class='unchanged'>").append(change.getOldQuantity()).append(" (Unchanged)</td>");
            } else {
                emailContent.append("<td>").append(change.getOldQuantity()).append("</td>");

                String quantityClass = change.getHighlightClass().equals("green") ? "increase" : (change.getHighlightClass().equals("red") ? "decrease" : "unchanged");
                emailContent.append("<td class='").append(quantityClass).append("'>").append(change.getNewQuantity()).append("</td>");
            }

            emailContent.append("<td>").append(change.getChange()).append("</td>");
            emailContent.append("</tr>");
        }

        emailContent.append("</table>");
        emailContent.append("<p> </p>");
        emailContent.append("<div class='footer'>Thank you for shopping with us! <br><b>Fossil Group</b></div>");
        emailContent.append("</div></body></html>");

        return emailContent.toString();
    }
}
