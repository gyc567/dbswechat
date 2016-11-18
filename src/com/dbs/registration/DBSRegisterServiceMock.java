package com.dbs.registration;

import com.dbs.datastore.DBStore;
import com.dbs.datastore.impl.H2DBStoreImpl;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/14/2016.
 */
public class DBSRegisterServiceMock {
    private static final DBStore dbStore=new H2DBStoreImpl();
    public boolean register(String userId,String password){
        String register = dbStore.register(userId, password);
        if (!"".equals(register)) {
            return true;
        }
        return false;
    }

    public String login(String userId,String password)
    {

        return "Login Fail!";
    }
}
