package com.yulu.mvnstudy.account.email;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.yulu.mvnstudy.account.AccountEmailService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.mail.Message;

import static junit.framework.Assert.assertEquals;


/**
 * Created by luyu on 2016/12/27.
 */
public class AccountEmailServiceTest {
    private GreenMail greenMail;

    @Before
    public void startMailServer() throws Exception {
        greenMail = new GreenMail(ServerSetup.SMTP);
        greenMail.setUser("luyu1999@163.com", "my8337649");
        greenMail.start();
    }
    @Test
    public  void testSendMail() throws Exception{
        ApplicationContext ctx=new ClassPathXmlApplicationContext("account-email.xml");
        AccountEmailService accountEmailService=(AccountEmailService)ctx.getBean("accountEmailService");
        String subject="Test Subject";
        String htmlText="<h3>Test</h3>";
        accountEmailService.sendEmail("luyu@situdata.com",subject,htmlText);
        greenMail.waitForIncomingEmail(2000, 1);
        Message[] msgs=greenMail.getReceivedMessages();
        assertEquals(1,msgs.length);
        assertEquals(subject, msgs[0].getSubject());
        System.out.println("测试");
        assertEquals(htmlText, (GreenMailUtil.getBody(msgs[0]).trim()));
    }
    @After
    public void stopMailServer() throws Exception{
        greenMail.stop();;
    }

}
