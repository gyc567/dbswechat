package com.dbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dbs.util.CheckUtil;

public class BindAccountServlet extends HttpServlet {
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/**
        req.setCharacterEncoding("UTF-8");
		
		String userName = req.getParameter("username");
		String userPwd = req.getParameter("password");
		
        
		System.out.println("user name is:" + userName);
		System.out.println("user password is:" + userPwd);
		**/
		String bind_suc = "http://dbswechat.wicp.io/dbswechat/Success.html";
		String bind_fail = "http://dbswechat.wicp.io/dbswechat/Fail.html";
	    //resp.sendRedirect(bind_suc);
	    
	    String userName = req.getParameter("username");
		String userPwd = req.getParameter("password");
		String weChatId = req.getParameter("weChat_id");
		
		boolean checkAccountFlag = false;
		
		
		/*if(userName == null || userPwd == null || weChatId == null){
			userName = (String) session.getAttribute("username");
			userPwd = (String) session.getAttribute("password");
			weChatId = (String) session.getAttribute("openId");
		}*/
		
		System.out.println("user name is:" + userName);
		System.out.println("user password is:" + userPwd);
		System.out.println("user weChat Id is:" + weChatId);
		
		checkAccountFlag = checkAccountExist(userName,userPwd);
		System.out.println("checkAccountFlag:" + checkAccountFlag);
	    
	    if(checkAccountFlag){
			resp.sendRedirect(bind_suc);
			}else{
			resp.sendRedirect(bind_fail);	
			}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String bindSuccessMsg = "bind account success!";
		
		String userName = req.getParameter("username");
		String userPwd = req.getParameter("password");
		String weChatId = req.getParameter("weChat_id");
		
//		System.out.println("user name is:" + userName);
//		System.out.println("user password is:" + userPwd);
//		System.out.println("user weChat Id is:" + weChatId);
		
		doGet(req,resp);
	}
	
	private boolean checkAccountExist(String userName, String userPwd){
		boolean accountExist = false;
		String userName1 = "SURESH";
		String userName2 = "ROBIN";
		String userName3 = "MARCOS";
		String userName4 = "ERIC";
		String password = "123456";
		List<String> acctList = new ArrayList<String>();
		
		acctList.add(userName1);
		acctList.add(userName2);
		acctList.add(userName3);
		acctList.add(userName4);
		
		if(userPwd != null){
		if(userPwd.equals(password)){
			for(String element:acctList){
				if(userName.equals(element)){
					accountExist = true;
					break;
				}else{continue;}
			}
		}
		}
		return accountExist;
	}

}
