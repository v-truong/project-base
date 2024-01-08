package com.example.security.event.listener;

import com.example.security.entity.Account;
import com.example.security.event.RegistrationCompleteEvent;
import com.example.security.event.SendcodeEmailEven;
import com.example.security.repo.AccountRepo;
import com.example.security.service.Implement.AccountSeviceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendcodeEmailEvenListener implements ApplicationListener<SendcodeEmailEven> {
    private final AccountSeviceImpl accountSeviceImpl;

    private final JavaMailSender mailSender;
    private Account theUser;
    @Autowired
    private AccountRepo accountRepo;


    @Override
    public void onApplicationEvent(SendcodeEmailEven event) {
        // 1. Get the newly registered user
        theUser = event.getAccount();
        //2. Create a verification token for the user
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int randomNumber = random.nextInt(max - min + 1) + min;

        //3. Save the verification token for the user
        Optional<Account> accOptional = accountRepo.findByEmail(theUser.getEmail());
        Account account = accOptional.get();
        account.setCode(randomNumber);
//        account.setTokenEmail(verificationToken);
        accountRepo.save(account);
        //4 Build the verification url to be sent to the user
        //5. Send the email.
        try {
            sendVerificationEmail(randomNumber);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendVerificationEmail(Integer ramdomcode) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email code";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, " + theUser.getName() + ", </p>" +
                "<p>Thank you for registering with us," + "" +
                "Please, follow the link below to complete your registration.</p>" +
                "<p >A new password : "+ramdomcode+"</p>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("dailycodework@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);

    }

}
