package com.dbs.test

import com.dbs.datastore.DBStore
import com.dbs.datastore.H2DBStoreUtil
import com.dbs.datastore.impl.H2DBStoreImpl
import spock.lang.Specification

import java.sql.Timestamp

/**
 * Created by eric567 [email:gyc567@126.com] 
 * on 11/14/2016.
 */
class H2DBStoreImplTest extends Specification {
    DBStore dbStore = null

    void setup() {

        dbStore = new H2DBStoreImpl()
    }

    void cleanup() {
        dbStore = null
    }

    def "QueryUserByWechatID"() {
        given: " the string of wechat id:Tom"
        String wechatID = "Tom"
        when: "invoke the  QueryUserByWechatID"
        String rt=dbStore.queryUserByWechatID(wechatID)

        then: "the result shall be Tom_dbs"
        rt.equals("Tom_dbs")==true
    }

    def "QueryUserByWechatID check the no exist wechatID shall return false"() {
        given: " the string of wechat id:Nobody"
        String wechatID = "nobody"
        when: "invoke the  QueryUserByWechatID"
        String rt=dbStore.queryUserByWechatID(wechatID)

        then: "the result shall be empty string"
        rt.equals("")==true
    }
    def "register the user and password shall return the not empty user ID"() {
        given: " the string of userID and password"
        String userID = "Jim"
        String pwd = "Jim_dbs123"
        when: "invoke the  register"
        String rt=dbStore.register(userID,pwd)

        then: "the result shall be Not empty string"
        rt.equals("")==false
        rt.equals("Jim")
    }

    def "getAccountBalanceFromAccountName give the name shall return the balance of the name"() {
        given: " user name"
        String name = "suresh"

        when: "invoke the  getAccountBalanceFromAccountName"
        int rt=dbStore.getAccountBalanceFromAccountName(name)

        then: "the result shall be Not negative balance"

        rt>=0
    }

    def "updateAccountBalanceFromAccountName give the name and new balance number shall return true"() {
        given: " user name"
        String name = "suresh"
        int newBalance=600004646
        String name2 = "eric"
        int newBalance2=60009039

        when: "invoke the  getAccountBalanceFromAccountName"
        boolean rt=dbStore.updateAccountBalanceFromAccountName(name,newBalance)
        boolean rt2=dbStore.updateAccountBalanceFromAccountName(name2,newBalance2)

        then: "the result shall be true"

        rt==true
        rt2==true
    }

    def "insertTransaction give the payer name,payee name,amout,date shall return true"() {
        given: " user name"
        String name = "suresh"
        String name2 = "eric"
        Timestamp date=H2DBStoreUtil.getCurrentTimestamp()
        int amount =100

        when: "invoke the method insertTransaction"
        boolean rt2=dbStore.insertTransaction(name,name2,amount,date)

        then: "the result shall be true"

        rt2==true
    }
    def "queryTransactionWithDateAndFromAccount give the payer name,start date and end date shall return list"() {
        given: " user name"
        String name = "suresh"
        Timestamp startDate= Timestamp.valueOf("2016-11-16 00:00:00")
        Timestamp enddate=Timestamp.valueOf("2016-11-17 00:00:00")

        when: "invoke the method insertTransaction"
        List rt2=dbStore.queryTransactionWithDateAndFromAccount(name, startDate,enddate)

        then: "the result shall be true"

        rt2.size()>0
    }

    def "queryTransactionWithDateAndToAccount give the payee name,start date and end date shall return list"() {
        given: " payee name"
        String name = "eric"
        Timestamp startDate= Timestamp.valueOf("2016-11-16 00:00:00")
        Timestamp enddate=Timestamp.valueOf("2016-11-17 00:00:00")

        when: "invoke the method insertTransaction"
        List rt2=dbStore.queryTransactionWithDateAndToAccount(name, startDate,enddate)

        then: "the result shall be true"

        rt2.size()>0
    }
}