package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day05Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day05(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 5, "example_1") | 3              | ""

        readFile(2025, 5)              | 885            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day05(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 5, "example_1") | 14             | ""

        readFile(2025, 5)              | 0              | ""
    }

}
