package com.yulu.mvnstudy.account.persist;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/1/2.
 */
public class AccountPersistServiceTest {
    private AccountPersistService service;

    @Before
    public void prepare() throws Exception {
        File persistDataFile = new File("target/test-classes/persist-data.xml");
        if (persistDataFile.exists()) {
            persistDataFile.delete();
        }
        ApplicationContext ctx = new ClassPathXmlApplicationContext("SpringFramework.xml");
        service = (AccountPersistService) ctx.getBean("accountPersistService");
        Account account = new Account();
        account.setId("001");
        account.setName("luyu");
        account.setEmail("luyu@163.com");
        account.setPassword("this_should_be_encrypted");
        account.setActivated(true);
        service.creatAccount(account);
    }

    @Test
    public void testReadAccount() throws Exception {
        Account account = service.readAccount("001");
        assertNotNull(account);
        assertEquals("001",account.getId());
        assertEquals("luyu",account.getName());
        assertEquals("luyu@163.com",account.getEmail());
        assertEquals("this_should_be_encrypted",account.getPassword());
        assertTrue(account.isActivated());

    }
}
