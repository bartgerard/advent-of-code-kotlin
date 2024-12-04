package aock2015

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2015Day08(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2015, 8, "example_1") | 8              | ""

        readFile(2015, 8)              | 0              | ">735"
    }

    def "partTwo"() {
        when:
        final long result = new Year2015Day08(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2015, 8) | 0              | ""
    }

}
