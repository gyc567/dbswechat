package com.dbs.test

import com.dbs.registration.UserRegisterService
import com.dbs.registration.impl.UserRegisterServiceImpl
import spock.lang.Specification

/**
 * Created by eric567 [email:gyc567@126.com] 
 * on 11/14/2016.
 */
class UserRegisterServiceTest extends Specification {
    UserRegisterService userRegisterService=null

    void setup() {
    userRegisterService=new UserRegisterServiceImpl()
    }

    void cleanup() {
    userRegisterService=null
    }

    def "isExist"() {
        given: " the string of wechat id:Tom"
        String wechatID = "Tom"
        when: "invoke the  isExist"
        boolean rt=userRegisterService.isExist(wechatID)

        then: "the result shall be true"
        rt==true
    }

    def "isExist check the no exist wechatID shall return false"() {
        given: " the string of wechat id:Nobody"
        String wechatID = "nobody"
        when: "invoke the  isExist"
        boolean rt=userRegisterService.isExist(wechatID)

        then: "the result shall be false"
        rt==false
    }
}
