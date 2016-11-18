package com.dbs.registration.impl;


import com.dbs.datastore.DBStore;
import com.dbs.datastore.impl.H2DBStoreImpl;
import com.dbs.registration.UserRegisterService;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/14/2016.
 */
public class UserRegisterServiceImpl implements UserRegisterService {
    DBStore h2DBStore=new H2DBStoreImpl();
    @Override
    public boolean isExist(String wechatID) {

        String s = h2DBStore.queryUserByWechatID(wechatID);
        if(!s.equals(""))return true;
        return false;
    }
}
