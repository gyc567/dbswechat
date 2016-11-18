package com.dbs.test

import com.dbs.tranfer.BankAccountTranferService
import com.dbs.tranfer.impl.BankAccountTranferServiceImpl
import spock.lang.Specification
/**
 * Created by eric567 [email:gyc567@126.com] 
 * on 11/16/2016.
 */
class BankAccountTranferServiceTest extends Specification {
    BankAccountTranferService bankAccountTranferService = null

    void setup() {
        bankAccountTranferService = new BankAccountTranferServiceImpl()

    }

    void cleanup() {
        bankAccountTranferService = null

    }

    def "QueryPayees"() {

        given: " the string of wechat id:suresh"
        String wechatID = "suresh"
        when: "invoke the  QueryPayees"
        List rt = bankAccountTranferService.queryPayees(wechatID)

        then: "the result shall be no empty list"
        rt.size() != 0

    }

    def "TranferMoney"() {
        given: " the string of from bank account:suresh,to bank account : robin,amount :100"
        String fromAccount = "robin"
        String toAccount = "eric"
        int amount=100
        when: "invoke the  TranferMoney"
        boolean  rt = bankAccountTranferService.tranferMoney(fromAccount,toAccount,amount)
        then: "the result shall be true"
        rt==true

    }

    def "tranferHistory give the   name,start date and end date shall return list"() {
        given: "   name"
        String name = "eric"
        String startDate=  "2016-11-16 00:00:00"
        String enddate= "2016-11-17 00:00:00"

        when: "invoke the method tranferHistory"
        List rt2=bankAccountTranferService.tranferHistory(name, startDate,enddate)

        then: "the result shall be true"

        rt2.size()>0
    }
}
