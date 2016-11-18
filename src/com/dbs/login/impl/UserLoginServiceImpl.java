package com.dbs.login.impl;

import com.dbs.login.UserLoginService;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/15/2016.
 */
public class UserLoginServiceImpl implements UserLoginService {
    @Override
    public boolean login() {
        return true;
    }

    @Override
    public boolean isLogin(String userID) {
        return true;
    }
}
