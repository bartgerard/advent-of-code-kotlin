package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day08(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 8, "example_1") | 14             | ""

        readFile(2024, 8)              | 348            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day08(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 8, "example_1") | 34             | ""
        readFile(2024, 8, "example_2") | 6              | ""

        readFile(2024, 8)              | 1221           | ""
    }

}
