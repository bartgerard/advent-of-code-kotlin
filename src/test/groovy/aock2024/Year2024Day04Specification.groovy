package aock2024

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day04Specification extends Specification {

    def "how many times does XMAS appear"() {
        when:
        final long result = new Year2024Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 4, "example_1") | 4              | ""
        readFile(2024, 4, "example_2") | 18             | ""
        readFile(2024, 4, "example_3") | 18             | ""

        readFile(2024, 4, "example_4") | 5              | ""
        readFile(2024, 4, "example_5") | 1              | ""
        readFile(2024, 4, "example_6") | 0              | ""

        readFile(2024, 4)              | 2618           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input                            | expectedResult | comment
        readFile(2024, 4, "example_b_1") | 9              | ""

        readFile(2024, 4)                | 2011           | ""
    }

}
