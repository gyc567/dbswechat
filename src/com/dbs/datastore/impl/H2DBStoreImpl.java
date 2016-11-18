package com.dbs.datastore.impl;


import com.dbs.datastore.DBStore;
import com.dbs.datastore.H2DBStoreUtil;
import com.dbs.model.Transaction;
import com.dbs.util.IdentifierGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/14/2016.
 */
public class H2DBStoreImpl implements DBStore {

    private static final String DBSID = "DBSID";

    @Override
    public String queryUserByWechatID(String wechatID)   {
        Connection dbConnection = H2DBStoreUtil.getDBConnection();
        String selectQuery = "select "+DBSID+"  from WECHAT_ID_DBS_ID  where WECHATID ='"+wechatID+"'";
        try {

            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            return resultSet.getString(DBSID);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";

    }

    @Override
    public String register(String userID, String password) {
        Connection dbConnection = H2DBStoreUtil.getDBConnection();
        String insertSQL = "INSERT INTO user " + "(userid, password) values" + "(?,?)";
        PreparedStatement insertPreparedStatement = null;
        try {
            dbConnection.setAutoCommit(false);
            insertPreparedStatement = dbConnection.prepareStatement(insertSQL);
            insertPreparedStatement.setString(1, userID.toLowerCase());
            insertPreparedStatement.setString(2, password);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            dbConnection.commit();
            return userID;

        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public int getAccountBalanceFromAccountName(String name) {
        int balance=0;
        Connection dbConnection = H2DBStoreUtil.getDBConnection();
        String selectQuery = "SELECT CUSTOMER_BANKACCTBAL   FROM CUSTOMER_BANKACCTINFO  where CUSTOMER_NAME  like '%"+name.toLowerCase()+"%'";
        try {

            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return balance;

    }


    //UPDATE CUSTOMER_BANKACCTINFO SET CUSTOMER_BANKACCTBAL=40000  WHERE CUSTOMER_NAME  like '%suresh%'
    @Override
    public boolean updateAccountBalanceFromAccountName(String name, int newBalance) {
        boolean rt =false;
        Connection dbConnection = H2DBStoreUtil.getDBConnection();
        String updateSQL = "UPDATE CUSTOMER_BANKACCTINFO SET CUSTOMER_BANKACCTBAL="+newBalance+"  WHERE   CUSTOMER_NAME  like '%"+name.toLowerCase()+"%'";
        try {
            dbConnection.setAutoCommit(false);
            PreparedStatement preparedStatement = dbConnection.prepareStatement(updateSQL);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            dbConnection.commit();
            rt =true;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rt;

    }

    @Override
    public boolean insertTransaction(String fromAccount, String toAccount, int amount, Timestamp transferDate) {
        Connection dbConnection = H2DBStoreUtil.getDBConnection();
        String insertSQL = "INSERT INTO TRANSACTION  " + "(PAYER_NAME, PAYEE_NAME,TRANS_DATE ,TRANS_AMOUNT,TRANS_ID ) values" + "(?,?,?,?,?)";
        PreparedStatement insertPreparedStatement = null;
        try {
            dbConnection.setAutoCommit(false);
            insertPreparedStatement = dbConnection.prepareStatement(insertSQL);
            insertPreparedStatement.setString(1, fromAccount.toLowerCase());
            insertPreparedStatement.setString(2, toAccount.toLowerCase());
            insertPreparedStatement.setTimestamp(3, transferDate);
            insertPreparedStatement.setInt(4, amount);
            insertPreparedStatement.setInt(5, IdentifierGenerator.getNextId());

            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            dbConnection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Transaction> queryTransactionWithDateAndFromAccount(String fromAccount, Timestamp startTransferDate, Timestamp endTransferDate) {
        List<Transaction> transactions=new ArrayList<>();
        Connection dbConnection = H2DBStoreUtil.getDBConnection();
        String selectQuery = "SELECT PAYER_NAME ,PAYEE_NAME , TRANS_AMOUNT ,TRANS_NOTE ,TRANS_DATE   FROM TRANSACTION " +
                "   where" +
                "   PAYER_NAME  like '%"+fromAccount.toLowerCase()+"%'" +
                " and TRANS_DATE Between '"+startTransferDate+"' And '"+endTransferDate+"' ";
        try {

            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Transaction transaction=new Transaction();

                transaction.setPayerName(resultSet.getString("PAYER_NAME"));
                transaction.setPayeeName(resultSet.getString("PAYEE_NAME"));
                transaction.setTransferNote(resultSet.getString("TRANS_NOTE"));
                transaction.setTransferDate(resultSet.getTimestamp("TRANS_DATE").toString());
                transaction.setTransferAmout(resultSet.getInt("TRANS_AMOUNT")+"");
                transactions.add(transaction);
            }

            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transactions;

    }
    @Override
    public List<Transaction> queryTransactionWithDateAndToAccount(String toAccount, Timestamp startTransferDate, Timestamp endTransferDate) {
        List<Transaction> transactions=new ArrayList<>();
        Connection dbConnection = H2DBStoreUtil.getDBConnection();
        String selectQuery = "SELECT PAYER_NAME ,PAYEE_NAME , TRANS_AMOUNT ,TRANS_NOTE ,TRANS_DATE   FROM TRANSACTION " +
                "   where" +
                "   PAYEE_NAME  like '%"+ toAccount.toLowerCase() +"%'" +
                " and TRANS_DATE Between '"+startTransferDate+"' And '"+endTransferDate+"' ";
        try {

            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Transaction transaction=new Transaction();

                transaction.setPayerName(resultSet.getString("PAYER_NAME"));
                transaction.setPayeeName(resultSet.getString("PAYEE_NAME"));
                transaction.setTransferNote(resultSet.getString("TRANS_NOTE"));
                transaction.setTransferDate(resultSet.getTimestamp("TRANS_DATE").toString());
                transaction.setTransferAmout(resultSet.getInt("TRANS_AMOUNT")+"");
                transactions.add(transaction);
            }



            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transactions;

    }


}
