package com.example.ecom.controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class VerificationMailerEntity {

    public void sendEmail(String toAddress,String otp) {

        // Recipient's email ID needs to be mentioned.


        // Sender's email ID needs to be mentioned
        String fromAddress = "19tuec054@skct.edu.in";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(fromAddress, "Youtubeforlife@09");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromAddress));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            String emailotp = toAddress+":"+otp;
            final String encodedPassword = org.apache.commons.codec.binary.Base64
                    .encodeBase64String(emailotp.getBytes());
System.out.println(encodedPassword);
            message.setText("<html><a href=http://localhost:8080/user/verify-email/"+encodedPassword+"/></html>a");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}