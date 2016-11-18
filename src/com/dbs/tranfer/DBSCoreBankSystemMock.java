package com.dbs.tranfer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/16/2016.
 */
public class DBSCoreBankSystemMock {

    public List queryPayees(String fromAccount) {
        List payees=new ArrayList<>();
        payees.add("robin");
        payees.add("eric");
        return payees;
    }
}
