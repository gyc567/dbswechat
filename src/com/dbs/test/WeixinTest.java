package com.dbs.test;

import com.dbs.po.AccessToken;
import com.dbs.util.WeixinUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author ljiah@cn.ibm.com
 * @date 2016-11-02
 * @version 0.1
 */

public class WeixinTest {

	public static void main(String[] args) {
		
		try{
		AccessToken token = WeixinUtil.getAccessToken();
		System.out.println("Token" + token.getToken());
		System.out.println("Valid Time period" + token.getExpiresIn());
		
		String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result = WeixinUtil.createMenu(token.getToken(), menu);
		if(result==0){
			System.out.println("Menu create successful");
		}else{
			System.out.println("Error code is:" + result);
		}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
