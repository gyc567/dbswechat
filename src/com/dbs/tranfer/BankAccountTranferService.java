package com.dbs.tranfer;

import com.dbs.model.Transaction;

import java.util.List;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/16/2016.
 */
public interface BankAccountTranferService {
    //get the payee list from DBS core bank system
    List queryPayees(String fromAccount);
    //transfer money from A to B
    boolean tranferMoney(String fromAccount, String toAccount,int amount);
    List<Transaction> tranferHistory(String fromAccount, String startTransferDate, String endTransferDate);
}
