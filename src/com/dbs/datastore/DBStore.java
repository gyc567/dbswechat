package com.dbs.datastore;

import com.dbs.model.Transaction;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/14/2016.
 */
public interface DBStore {
    String queryUserByWechatID(String wechatID);
    String register(String userID,String password);
    int getAccountBalanceFromAccountName(String name);
    boolean updateAccountBalanceFromAccountName(String name,int newBalance);
    boolean insertTransaction(String fromAccount,String toAccount,int amount,Timestamp transferDate);
    List<Transaction> queryTransactionWithDateAndFromAccount(String fromAccount, Timestamp startTransferDate, Timestamp endTransferDate);
    List<Transaction> queryTransactionWithDateAndToAccount(String toAccount, Timestamp startTransferDate, Timestamp endTransferDate);
}
