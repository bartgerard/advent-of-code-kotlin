package aock2020


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2020Day08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2020Day08(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2020, 8, "example_1") | 5              | ""

        readFile(2020, 8)              | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2020Day08(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2020, 8) | 0              | ""
    }

}
