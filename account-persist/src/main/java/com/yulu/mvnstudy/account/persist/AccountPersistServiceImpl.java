package com.yulu.mvnstudy.account.persist;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
public class AccountPersistServiceImpl implements AccountPersistService {
    private static final String ELEMENT_ROOT = "account_persist";
    private static final String ELEMENT_ACCOUNTS = "accounts";
    private static final String ELEMENT_ACCOUNT_ID = "id";
    private static final String ELEMENT_ACCOUNT_EMAIL = "email";
    private static final String ELEMENT_ACCOUNT_PASSWORD = "password";
    private static final String ELEMENT_ACCOUNT_NAME = "name";
    private static final String ELEMENT_ACCOUNT_ACTIVATED="activated";
    private static final String ELEMENT_ACCOUNT = "account";

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private String file;
    private SAXReader reader = new SAXReader();

    private Document readDocument() throws AccountPersistException {
        File datafile = new File(file);
        if (!datafile.exists()) {
            datafile.getParentFile().mkdir();
            Document doc= DocumentFactory.getInstance().createDocument();
            Element rootEle=doc.addElement(ELEMENT_ROOT);
            rootEle.addElement(ELEMENT_ACCOUNTS);
            writeDocument(doc);
        }
        try {
            return reader.read(new File(file));
        }catch (DocumentException e){
            throw new AccountPersistException("unable read persist data.xml");
        }
    }
    private void writeDocument(Document doc) throws AccountPersistException {
        Writer out=null;
        try {
            out=new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            XMLWriter writer= new XMLWriter(out, OutputFormat.createPrettyPrint());
            writer.write(doc);
        } catch (IOException e) {
            throw new AccountPersistException("Unable Write");
        }
        finally {
            if(out !=null){
                try {
                    out.close();
                } catch (IOException e) {
                    throw new AccountPersistException("Unable close out");
                }
            }
        }
    }

    @Override
    public void creatAccount(Account account) throws AccountPersistException {
        Document doc=readDocument();
        Element accountsEle=doc.getRootElement().element(ELEMENT_ACCOUNTS);
        Element accountEle=accountsEle.addElement(ELEMENT_ACCOUNT);
        accountEle.addElement(ELEMENT_ACCOUNT_ID).addText(account.getId());
        accountEle.addElement(ELEMENT_ACCOUNT_NAME).addText(account.getName());
        accountEle.addElement(ELEMENT_ACCOUNT_EMAIL).addText(account.getEmail());
        accountEle.addElement(ELEMENT_ACCOUNT_PASSWORD).addText(account.getPassword());
        accountEle.addElement(ELEMENT_ACCOUNT_ACTIVATED).addText(account.isActivated()==true?"true":"fasle");
        writeDocument(doc);
    }

    @Override
    public Account readAccount(String id) throws AccountPersistException {
        Document doc=readDocument();
        Element accountsEle=doc.getRootElement().element(ELEMENT_ACCOUNTS);
        for(Element accountElm:(List<Element>)accountsEle.elements()){
            if(accountElm.elementText(ELEMENT_ACCOUNT_ID).equalsIgnoreCase(id)){
                return buildAccount(accountElm);
            }
        }
        return null;
    }

    private Account buildAccount(Element accountElm) {
        Account account=new Account();
        account.setId(accountElm.elementText(ELEMENT_ACCOUNT_ID));
        account.setEmail(accountElm.elementText(ELEMENT_ACCOUNT_EMAIL));
        account.setName(accountElm.elementText(ELEMENT_ACCOUNT_NAME));
        account.setPassword(accountElm.elementText(ELEMENT_ACCOUNT_PASSWORD));
        account.setActivated("true".equalsIgnoreCase(accountElm.elementText(ELEMENT_ACCOUNT_ACTIVATED))?true:false);
        return account;
    }

    @Override
    public Account updateAccount(Account account) throws AccountPersistException {
        return null;
    }

    @Override
    public void deleteAccount(String id) throws AccountPersistException {

    }
}
