package com.dbs.datastore.impl;

import com.dbs.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/14/2016.
 */
public class MysqlDBStoreImpl //implements DBStore
{
    private static final String DBSID = "DBSID";


    public String queryUserByWechatID(String wechatID)   {
        Connection dbConnection = null;
        try {
            dbConnection = DbUtil.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String selectQuery = "select "+DBSID+"  from WECHAT_ID_DBS_ID  where WECHATID ='"+wechatID+"'";
        try {

            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            String nValue = resultSet.getString(DBSID);
            return resultSet.wasNull() ? "" : nValue;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }


    public String register(String userID, String password) {
        Connection dbConnection = null;
        try {
            dbConnection = DbUtil.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String InsertQuery = "INSERT INTO user " + "(userid, password) values" + "(?,?)";
        PreparedStatement insertPreparedStatement = null;
        try {
            insertPreparedStatement = dbConnection.prepareStatement(InsertQuery);
            insertPreparedStatement.setString(1, userID);
            insertPreparedStatement.setString(2,password);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            return userID;

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return "";
    }

}
