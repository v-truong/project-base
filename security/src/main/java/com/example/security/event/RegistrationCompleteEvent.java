package com.example.security.event;

import com.example.security.entity.Account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Sampson Alfred
 */
@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private Account account;
    private String applicationUrl;

    public RegistrationCompleteEvent(Account account, String applicationUrl) {
        super(account);
        this.account = account;
        this.applicationUrl = applicationUrl;
    }
}
