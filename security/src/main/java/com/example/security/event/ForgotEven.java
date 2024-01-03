package com.example.security.event;

import com.example.security.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter

public class ForgotEven extends ApplicationEvent {
    private Account account;
    private String applicationUrl;

    public ForgotEven(Account account, String applicationUrl) {
        super(account);
        this.account = account;
        this.applicationUrl = applicationUrl;
    }
}
