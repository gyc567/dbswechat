package com.dbs.util;

import com.dbs.login.UserLoginService;
import com.dbs.login.impl.UserLoginServiceImpl;
import com.dbs.model.Transaction;
import com.dbs.model.TransferStatus;
import com.dbs.po.News;
import com.dbs.po.NewsMessage;
import com.dbs.po.TextMessage;
import com.dbs.registration.UserRegisterService;
import com.dbs.registration.impl.UserRegisterServiceImpl;
import com.dbs.tranfer.BankAccountTranferService;
import com.dbs.tranfer.impl.BankAccountTranferServiceImpl;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author ljiah@cn.ibm.com
 * @version 0.1
 * @date 2016-11-11
 */

public class MessageUtil {

    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";
    public static final String MESSAGE_TEXT = "text";
    private static final UserRegisterService userRegisterService = new UserRegisterServiceImpl();
    private static final UserLoginService userLoginService = new UserLoginServiceImpl();
    private static final BankAccountTranferService bankAccountTranferService = new BankAccountTranferServiceImpl();
    private static final Map sessionCache = new HashMap<>();

    /**
     * XML Convert to Map
     *
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();

        List<Element> list = root.elements();

        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }

        ins.close();

        return map;
    }

    public static String textMessageToXml(TextMessage textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    public static String initText(String toUserName, String fromUserName, String content) {
        TextMessage text = buildTextMessage(fromUserName, toUserName);
        text.setContent(content);
        return textMessageToXml(text);
    }

    public static String userLoginTextMessage(String toUserName, String fromUserName) {
        TextMessage text = buildTextMessage(fromUserName, toUserName);
        if (userRegisterService.isExist(fromUserName)) {

            String loginSuccessContent = "user login successfully";
            text.setContent(loginSuccessContent);
        } else {//redirect to DBS register service


        }
        return textMessageToXml(text);
    }

    public static String transferShowPayeeListTextMessage(String toUserName, String fromUserName) {
        TextMessage text = buildTextMessage(fromUserName, toUserName);
        List payees = bankAccountTranferService.queryPayees(fromUserName);
        if (userLoginService.isLogin(fromUserName)) {
            if (null == sessionCache.get(fromUserName + WeixinUtil.FUND_TRANSFER)) {

                Transaction transaction = new Transaction();
                transaction.setTranferStatus(TransferStatus.QUERY_PAYEES);
                sessionCache.put(fromUserName + WeixinUtil.FUND_TRANSFER, transaction);
            } else {
                Transaction tn = (Transaction) sessionCache.get(fromUserName + WeixinUtil.FUND_TRANSFER);
                tn.setTranferStatus(TransferStatus.QUERY_PAYEES);
                sessionCache.put(fromUserName + WeixinUtil.FUND_TRANSFER, tn);

            }
        }
        StringBuffer content = new StringBuffer("--payee list--");
        content.append("/n");
        for (int i = 0; i < payees.size(); i++) {
            String payee = (String) payees.get(i);
            content.append("--" + " " + payee);
            content.append("/n");

        }
        content.append("please input one payee!");
        text.setContent(content.toString());
        return textMessageToXml(text);
    }

    /**
     * Welcome Menu
     *
     * @return
     */
    public static String menuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("Welcome to DBS WeChat Bank.");
        return sb.toString();
    }

    /**
     * Check Balance Text
     *
     * @return
     */
    public static String checkBalance(String cust_id) {
        StringBuffer sb = new StringBuffer();
        DbUtil dbUtil = new DbUtil();
        String balance = dbUtil.checkbalance(cust_id);
        sb.append("Your current balance is: " + balance + ".");
        return sb.toString();
    }

    /**
     * Change Text with Pic message to XML
     *
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new News().getClass());
        return xstream.toXML(newsMessage);
    }


    /**
     * Construction of Text with Pic message type
     *
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNewsMessage(String toUserName, String fromUserName) {
        String message = null;
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("Welcome To DBS");
        news.setDescription("With DBS WeChat Banking, you have complete control of your finances at your fingertips with unparalleled security and flexibility.");
        news.setPicUrl("http://dbswechat.wicp.io/dbswechat/image/WelcomePage.JPG");
        news.setUrl("https://internet-banking.hk.dbs.com/IB/Welcome");

        newsList.add(news);

        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = newsMessageToXml(newsMessage);
        return message;
    }

    /**
     * Construction of Text with Pic message type
     *
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initLoginMessage(String toUserName, String fromUserName) {
        String message = null;
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();
        String weChat_id = fromUserName;

        News news = new News();
        news.setTitle("Bind DBS Account");
        news.setDescription("Click here to bind your DBS account to DBS WeChat Banking, you can have check account balance, fund transfer to other payee account and check transaction history services.");
        news.setPicUrl("http://dbswechat.wicp.io/dbswechat/image/BindAccount.JPG");
        //news.setUrl("https://internet-banking.hk.dbs.com/IB/Welcome");
        news.setUrl("http://dbswechat.wicp.io/dbswechat/login-1.html" + "?" + "weChat_id=" + weChat_id);

        newsList.add(news);

        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = newsMessageToXml(newsMessage);
        return message;
    }

    public static String handlePayee(String fromUserName, String toUserName, String content) {

        TextMessage text = buildTextMessage(fromUserName, toUserName);
        if (userLoginService.isLogin(fromUserName)) {
            if (null == sessionCache.get(fromUserName + WeixinUtil.FUND_TRANSFER)) {

                content = "didn't initail session cache!";

            } else {
                Transaction tn = (Transaction) sessionCache.get(fromUserName + WeixinUtil.FUND_TRANSFER);
                tn.setPayeeName(content);
                tn.setTranferStatus(TransferStatus.ADD_AMOUNT);
                sessionCache.put(fromUserName + WeixinUtil.FUND_TRANSFER, tn);

                content = "please input the amount.";
            }
        }
        text.setContent(content.toString());
        return textMessageToXml(text);

    }

    public static TransferStatus getTransferStatus(String fromUserName) {
        Transaction tn = (Transaction) sessionCache.get(fromUserName + WeixinUtil.FUND_TRANSFER);

        return tn.getTranferStatus();
    }

    public static String handleAddAmount(String fromUserName, String toUserName, String content) {
        TextMessage text = buildTextMessage(fromUserName, toUserName);
        if (userLoginService.isLogin(fromUserName)) {
            if (null == sessionCache.get(fromUserName + WeixinUtil.FUND_TRANSFER)) {

                content = "didn't initail session cache!";

            } else {
                Transaction tn = (Transaction) sessionCache.get(fromUserName + WeixinUtil.FUND_TRANSFER);
                tn.setTransferAmout(content);
                tn.setTranferStatus(TransferStatus.ADD_COMMENTS);
                sessionCache.put(fromUserName + WeixinUtil.FUND_TRANSFER, tn);

                content = "please input the comments.";
            }
        }
        text.setContent(content.toString());
        return textMessageToXml(text);
    }

    private static TextMessage buildTextMessage(String fromUserName, String toUserName) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        return text;
    }

    public static String handleAddComments(String fromUserName, String toUserName, String content) {
        TextMessage text = buildTextMessage(fromUserName, toUserName);
        StringBuffer detail = new StringBuffer("");
        if (userLoginService.isLogin(fromUserName)) {
            if (null == sessionCache.get(fromUserName + WeixinUtil.FUND_TRANSFER)) {

                content = "didn't initail session cache!";

            } else {
                Transaction tn = (Transaction) sessionCache.get(fromUserName + WeixinUtil.FUND_TRANSFER);
                tn.setTransferNote(content);
                tn.setTranferStatus(TransferStatus.TRANSFER_CONFIRM);
                tn.setPayeeName(toUserName);
                tn.setPayerName(fromUserName);
                sessionCache.put(fromUserName + WeixinUtil.FUND_TRANSFER, tn);


                content = "here is the detail of this transaction./n";

                detail.append("from: " + fromUserName);
                detail.append("/n");
                detail.append("to: " + toUserName);
                detail.append("/n");
                detail.append("amount: " + tn.getTransferAmout());
                detail.append("/n");
                detail.append("comments: " + tn.getTransferNote());
                detail.append("/n");

                detail.append("please input: /n");
                detail.append("1.confirm /n");
                detail.append("2.reset /n");

            }
        }

        text.setContent(content.toString() + detail.toString());
        return textMessageToXml(text);
    }
}
