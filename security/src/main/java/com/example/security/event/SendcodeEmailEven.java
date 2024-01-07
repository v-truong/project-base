package com.example.security.event;

import com.example.security.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SendcodeEmailEven extends ApplicationEvent {
    private Account account;


    public SendcodeEmailEven(Account account) {
        super(account);
        this.account = account;

    }
}
