package aock2016


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2016Day06Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2016Day06(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2016, 6) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2016Day06(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2016, 6) | 0              | ""
    }

}
