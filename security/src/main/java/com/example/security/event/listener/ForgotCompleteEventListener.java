package com.example.security.event.listener;

import com.example.security.entity.Account;

import com.example.security.event.ForgotEven;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForgotCompleteEventListener implements ApplicationListener<ForgotEven> {
    private final AccountSeviceImpl accountSeviceImpl;

    private final JavaMailSender mailSender;
    private Account theUser;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ForgotEven event) {
        // 1. Get the newly registered user
        theUser = event.getAccount();
        //2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        //3. Save the verification token for the user
        Optional<Account> accOptional = accountRepo.findByUsername(theUser.getUsername());
        Account account =accOptional.get();
        account.setPassword(passwordEncoder.encode(verificationToken));
        accountRepo.save(account);
        //4 Build the verification url to be sent to the user
        String url =verificationToken;
        //5. Send the email.
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }



    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email forgot pass";
        String senderName = "The user logs into the portal to change the password";
        String mailContent = "<p> Hi, "+ theUser.getName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Now log in with the new password we provided.</p>"+
                "<p >A new password : "+url+"</p>"+
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
