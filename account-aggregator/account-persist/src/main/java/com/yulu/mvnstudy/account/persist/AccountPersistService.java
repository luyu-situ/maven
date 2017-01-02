package com.yulu.mvnstudy.account.persist;

/**
 * Created by Administrator on 2017/1/2.
 */
public interface AccountPersistService {
    void creatAccount(Account account) throws AccountPersistException;
    Account readAccount(String id)throws AccountPersistException;
    Account updateAccount(Account account)throws AccountPersistException;
    void deleteAccount(String id)throws AccountPersistException;
}
