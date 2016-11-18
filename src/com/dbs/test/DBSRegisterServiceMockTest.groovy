package com.dbs.test

import com.dbs.registration.DBSRegisterServiceMock
import spock.lang.Specification

/**
 * Created by eric567 [email:gyc567@126.com] 
 * on 11/15/2016.
 */
class DBSRegisterServiceMockTest extends Specification {
    DBSRegisterServiceMock dbsRegisterServiceMock = null

    void setup() {
        dbsRegisterServiceMock = new DBSRegisterServiceMock()
    }

    void cleanup() {
        dbsRegisterServiceMock = null
    }

    def "Register"() {
        given:"user id and password"
        String userID="AnyBody"
        String password="SSS"
        when:"invoke the register method"
        boolean rt=dbsRegisterServiceMock.register(userID,password)
        then:"the result shall be true"
        rt==true

    }
}
