package com.dbs.test

import com.dbs.util.IdentifierGenerator
import spock.lang.Specification

/**
 * Created by eric567 [email:gyc567@126.com] 
 * on 11/16/2016.
 */
class IdentifierGeneratorTest extends Specification {
    IdentifierGenerator identifierGenerator = null

    void setup() {
        identifierGenerator = new IdentifierGenerator()
    }

    void cleanup() {
        identifierGenerator = null
    }

    def "test the method getNextId rt"() {

        given:"the instance of IdentifierGenerator "

        when:"invoke the method getNextId"

        int id=identifierGenerator.getNextId();
        then:"get the id is not zero"
        id>0
    }
}
