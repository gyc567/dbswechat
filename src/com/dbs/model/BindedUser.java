package com.dbs.model;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/17/2016.
 */
public class BindedUser {
private String wechatID;
private String dbsBankAccountID;
private String bindedStatus;

    public String getWechatID() {
        return wechatID;
    }

    public void setWechatID(String wechatID) {
        this.wechatID = wechatID;
    }

    public String getDbsBankAccountID() {
        return dbsBankAccountID;
    }

    public void setDbsBankAccountID(String dbsBankAccountID) {
        this.dbsBankAccountID = dbsBankAccountID;
    }

    public String getBindedStatus() {
        return bindedStatus;
    }

    public void setBindedStatus(String bindedStatus) {
        this.bindedStatus = bindedStatus;
    }
}
