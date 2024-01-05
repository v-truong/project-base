package com.example.security.event;

import com.example.security.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter

public class ForgotEven extends ApplicationEvent {
    private Account account;


    public ForgotEven(Account account) {
        super(account);
        this.account = account;

    }
}
