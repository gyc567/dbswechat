package com.dbs.servlet;

import com.dbs.model.TransferStatus;
import com.dbs.util.CheckUtil;
import com.dbs.util.MessageUtil;
import com.dbs.util.WeixinUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author ljiah@cn.ibm.com
 * @version 0.1
 * @date 2016-11-01
 */

public class DbswechatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //super.doGet(req, resp);
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        PrintWriter out = resp.getWriter();
        if (CheckUtil.checkSign(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            Map<String, String> map = MessageUtil.xmlToMap(req);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String customer_id = "60001";

            String message = null;
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {

                if (TransferStatus.QUERY_PAYEES.equals(MessageUtil.getTransferStatus(fromUserName))) {

                    message = MessageUtil.handlePayee(fromUserName, toUserName, content);
                }
                if (TransferStatus.ADD_AMOUNT.equals(MessageUtil.getTransferStatus(fromUserName))) {

                    message = MessageUtil.handleAddAmount(fromUserName, toUserName, content);
                }
                if (TransferStatus.ADD_COMMENTS.equals(MessageUtil.getTransferStatus(fromUserName))) {

                    message = MessageUtil.handleAddComments(fromUserName, toUserName, content);
                }
            }


            if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                String eventType = map.get("Event");
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    //message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                    System.out.println(message);
                }


                if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
                    String keyValue = map.get("EventKey");
                    if (keyValue.equals(WeixinUtil.HOME)) {
                        message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                    } else if (keyValue.equals(WeixinUtil.LOGIN)) {//user log in to DBS bank system
                        //MessageUtil.userLoginTextMessage(toUserName, fromUserName);
                        message = MessageUtil.initLoginMessage(toUserName, fromUserName);

                    } else if (keyValue.equals(WeixinUtil.FUND_TRANSFER)) {//user click the tranfer btn will show the payee list
                        MessageUtil.transferShowPayeeListTextMessage(toUserName, fromUserName);
                    } else if (keyValue.equals(WeixinUtil.CHECK_BALANCE)) {
                        message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.checkBalance(customer_id));
                    }
                }
            }

            out.print(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }


}
