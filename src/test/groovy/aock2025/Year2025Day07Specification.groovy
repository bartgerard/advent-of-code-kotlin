package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day07Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day07(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        ""                             | 0              | ""
        readFile(2025, 7, "example_1") | 0              | ""

        readFile(2025, 7)              | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day07(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        ""                             | 0              | ""
        readFile(2025, 7, "example_1") | 0              | ""

        readFile(2025, 7)              | 0              | ""
    }

}
