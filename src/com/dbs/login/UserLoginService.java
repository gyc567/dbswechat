package com.dbs.login;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/15/2016.
 */
public interface UserLoginService {
    boolean login();
    boolean isLogin(String userID);
}
