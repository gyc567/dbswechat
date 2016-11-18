package com.dbs.util;

import com.dbs.menu.Button;
import com.dbs.menu.ClickButton;
import com.dbs.menu.Menu;
import com.dbs.po.AccessToken;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 
 * @author ljiah@cn.ibm.com
 * @date 2016-11-10
 * @version 0.1
 */

public class WeixinUtil {
	
	public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	public static final String APPID = "wx280d4dc04187a58a";
	public static final String APPSECRET = "39b7e4bf955023411522a41c800c86c8";
	public static final String HOME = "11";
	public static final String LOGIN = "12";
	public static final String CHECK_BALANCE = "13";
	public static final String TRANFER_HISTORY = "14";
	public static final String TRANFER_CONFIRM = "15";
	public static final String FUND_TRANSFER = "21";

	/**
	 * Create Self-defined Menu Function
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		Button accountButton = new Button();
		accountButton.setName("Account");
		
		ClickButton homeButton = new ClickButton();
		homeButton.setName("Home");
		homeButton.setType("click");
		homeButton.setKey(HOME);
		
		ClickButton loginButton = new ClickButton();
		loginButton.setName("Login");
		loginButton.setType("click");
		loginButton.setKey(LOGIN);
		
		ClickButton checkBalanceButton = new ClickButton();
		checkBalanceButton.setName("Check Balance");
		checkBalanceButton.setType("click");
		checkBalanceButton.setKey(CHECK_BALANCE);
		
		ClickButton tranferHistoryButton = new ClickButton();
		tranferHistoryButton.setName("Tran History");
		tranferHistoryButton.setType("click");
		tranferHistoryButton.setKey(TRANFER_HISTORY);

		Button transferButton = new Button();
		transferButton.setName("Transfer");

		
		ClickButton fundTransferButton = new ClickButton();
		fundTransferButton.setName("Fund Transfer");
		fundTransferButton.setType("click");
		fundTransferButton.setKey(FUND_TRANSFER);
		/**
		ViewButton button21 = new ViewButton();
		button21.setName("view");
		button21.setType("view");
		button21.setUrl("http://www.baidu.com");
		
		ClickButton button31 = new ClickButton();
		button31.setName(" ");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName(" ");
		button32.setType("location_select");
		button32.setKey("32");
		**/
		
		accountButton.setSub_button(new Button[]{homeButton, loginButton, checkBalanceButton, tranferHistoryButton});
		transferButton.setSub_button(new Button[]{fundTransferButton});
		
		menu.setButton(new Button[]{accountButton, transferButton});
		return menu;
	}
	
	public static int createMenu(String token,String menu){
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url,menu);
		if(jsonObject!=null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	/**
	 * get request
	 * @param url
	 * @return
	 */
	public static JSONObject doGetStr(String url){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * post request
	 * @param url
	 * @param outStr
	 * @return
	 */
	public static JSONObject doPostStr(String url,String outStr){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		
		
		try {
			httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * Get accessToken
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
}
