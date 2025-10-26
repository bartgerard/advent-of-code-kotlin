package aock2016


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2016Day02Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2016Day02(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2016, 2, "example_1") | 1985           | ""


        readFile(2016, 2)              | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2016Day02(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2016, 2) | 0              | ""
    }

}
