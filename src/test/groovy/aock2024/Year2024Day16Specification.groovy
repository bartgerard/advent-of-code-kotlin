package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day16Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day16(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 16, "example_1") | 7036           | ""
        readFile(2024, 16, "example_2") | 11048          | ""

        readFile(2024, 16)              | 94436          | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day16(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 16, "example_1") | 45             | ""
        readFile(2024, 16, "example_2") | 64             | ""

        readFile(2024, 16)              | 481            | ""
    }

}
