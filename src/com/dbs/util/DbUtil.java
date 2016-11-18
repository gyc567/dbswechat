package com.dbs.util;

import java.sql.*;

/**
 * 
 * @author ljiah@cn.ibm.com
 * @date 2016-11-12
 * @version 0.1
 */

public class DbUtil {
	static boolean bInited = false;
	
	public  static void initJDBC() throws ClassNotFoundException {
	Class.forName("com.mysql.jdbc.Driver");
	bInited = true;
	//System.out.println("Success loading Mysql Driver!");
	}

	public static Connection getConnection() throws ClassNotFoundException,SQLException{
	if(!bInited){
	    initJDBC();
	}

	Connection conn = DriverManager.getConnection(
	"jdbc:mysql://localhost:3306/dbswechat","root","123123");
	return conn;
	}


	public String checkbalance(String custId){
	String balance = null;
	String sql = "SELECT * FROM CUSTOMER_BANKACCTINFO WHERE CUSTOMER_ID = '" + custId + "'";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	try{
	conn = getConnection();
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	while(rs.next()){
	  balance = rs.getString("customer_bankacctbal");
	break;
	}
	}catch (ClassNotFoundException e) {
	  e.printStackTrace();
	}catch (SQLException e) {
	  e.printStackTrace();
	}
	return balance;
	}

}
