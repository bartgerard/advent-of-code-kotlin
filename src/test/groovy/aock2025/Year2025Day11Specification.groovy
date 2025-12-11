package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day11Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day11(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2025, 11, "example_1") | 5              | ""

        readFile(2025, 11)              | 733            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day11(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2025, 11, "example_1") | 0              | ""

        readFile(2025, 11)              | 0              | ""
    }

}
