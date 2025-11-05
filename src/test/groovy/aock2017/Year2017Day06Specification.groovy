package aock2017

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2017Day06Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2017Day06(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "0,2,7,0"         | 5              | ""

        readFile(2017, 6) | 3156           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2017Day06(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "0,2,7,0"         | 4              | ""

        readFile(2017, 6) | 1610           | ""
    }

}
