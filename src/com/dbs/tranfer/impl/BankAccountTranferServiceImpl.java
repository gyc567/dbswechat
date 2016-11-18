package com.dbs.tranfer.impl;

import com.dbs.datastore.DBStore;
import com.dbs.datastore.H2DBStoreUtil;
import com.dbs.datastore.impl.H2DBStoreImpl;
import com.dbs.model.Transaction;
import com.dbs.tranfer.BankAccountTranferService;
import com.dbs.tranfer.DBSCoreBankSystemMock;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/16/2016.
 */
public class BankAccountTranferServiceImpl implements BankAccountTranferService {
    public static final DBStore dbStore = new H2DBStoreImpl();

    @Override
    public List queryPayees(String fromAccount) {
        return new DBSCoreBankSystemMock().queryPayees(fromAccount);
    }

    @Override
    public boolean tranferMoney(String fromAccount, String toAccount, int amount) {

        //1.decrease the fromAccount by amount
        //1.1 get balance of fromAccount
        int fromBalance = dbStore.getAccountBalanceFromAccountName(fromAccount);
        //1.2 decrease the from balance
        int newFromBalnae = fromBalance - amount;
        //1.3 update the new balance to from account
        boolean isUpdateFromAccountBalanceSuccess = dbStore.updateAccountBalanceFromAccountName(fromAccount, newFromBalnae);
        boolean isUpdateToAccountBalanceSuccess=false;
        boolean isInsertToTransactionSuccess=false;

        //2.increase the toAccount by amount
        if (isUpdateFromAccountBalanceSuccess) {

            //2.1 get balance of toAccount
            int toBalance = dbStore.getAccountBalanceFromAccountName(toAccount);
            //2.2 increase the to balance
            int newToBalnae = toBalance + amount;
            //2.3 update the new balance to to account
            isUpdateToAccountBalanceSuccess = dbStore.updateAccountBalanceFromAccountName(toAccount, newToBalnae);

        }

        //insert the transfer msg to transaction table
        if(isUpdateFromAccountBalanceSuccess &&isUpdateToAccountBalanceSuccess)
        {
          isInsertToTransactionSuccess = dbStore.insertTransaction(fromAccount, toAccount, amount, H2DBStoreUtil.getCurrentTimestamp());
        }

        //3.return true,by meaning transfer success
        return isUpdateToAccountBalanceSuccess&& isUpdateFromAccountBalanceSuccess&&isInsertToTransactionSuccess;
    }

    @Override
    public List<Transaction> tranferHistory(String fromAccount, String startTransferDate, String endTransferDate) {

        List<Transaction> transactions = dbStore.queryTransactionWithDateAndFromAccount(fromAccount, Timestamp.valueOf(startTransferDate), Timestamp.valueOf(endTransferDate));
        List<Transaction> transactions2 = dbStore.queryTransactionWithDateAndToAccount(fromAccount, Timestamp.valueOf(startTransferDate), Timestamp.valueOf(endTransferDate));

        transactions.addAll(transactions2);
        return transactions;
    }
}
