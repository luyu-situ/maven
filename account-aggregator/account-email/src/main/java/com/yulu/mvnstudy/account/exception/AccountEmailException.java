package com.yulu.mvnstudy.account.exception;

import javax.mail.MessagingException;

/**
 * Created by luyu on 2016/12/27.
 */
public class AccountEmailException extends Exception {
    public AccountEmailException(String s, MessagingException e) {
    }
}
