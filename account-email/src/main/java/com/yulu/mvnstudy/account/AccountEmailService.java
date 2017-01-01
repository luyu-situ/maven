package com.yulu.mvnstudy.account;

import com.yulu.mvnstudy.account.exception.AccountEmailException;

/**
 * Created by luyu on 2016/12/27.
 */
public interface AccountEmailService {
    void  sendEmail(String to,String subject,String htmlText) throws AccountEmailException;
}
