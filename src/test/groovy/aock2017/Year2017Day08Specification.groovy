package aock2017

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2017Day08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2017Day08(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2017, 8) | -1             | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2017Day08(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2017, 8) | -1             | ""
    }

}
