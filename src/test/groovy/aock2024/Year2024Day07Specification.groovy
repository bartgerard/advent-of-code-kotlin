package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day07Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day07(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        "292: 11 6 16 20"              | 292            | ""
        readFile(2024, 7, "example_1") | 3749           | ""

        readFile(2024, 7)              | 2437272016585  | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day07(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        "156: 15 6"                    | 156            | ""
        readFile(2024, 7, "example_1") | 11387          | ""

        readFile(2024, 7)              | 0              | ""
    }

}
