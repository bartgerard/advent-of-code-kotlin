package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day21Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day21(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        "029A"                          | 68 * 29        | ""
        "980A"                          | 60 * 980       | ""
        "179A"                          | 68 * 179       | ""
        "456A"                          | 64 * 456       | ""
        "379A"                          | 64 * 379       | ""
        readFile(2024, 21, "example_1") | 126384         | ""

        readFile(2024, 21)              | 215374         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day21(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        readFile(2024, 21) | 0              | ""
    }

}
