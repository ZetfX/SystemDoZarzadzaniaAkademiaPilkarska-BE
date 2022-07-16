package edu.uni.lodz.system.akademia.pilkarska.application.senders;


import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;

import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class EmailSender {

    public void sendEmail(String recipient, String password) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String myAccountEmail = "system.akademia.pilkarska@gmail.com";
        String myEmailPassword = "ohungcoclqkybekl";
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, myEmailPassword);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recipient,password);
        try {
            Transport.send(message);
            log.info("Wysłano wiadomość" + message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }


    private Message prepareMessage(Session session, String email, String recipient, String password) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Dane Logowania");
            message.setText("Login: " + recipient + "\nhasło: " + password + "\nProsimy zmienić hasło po pierwszym logowaniu!!!!!");
            return message;
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
