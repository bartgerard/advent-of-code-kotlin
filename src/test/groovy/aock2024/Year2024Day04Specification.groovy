package aock2024

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 4, "example_1")  | 10             | ""
        readFile(2024, 4, "example_2")  | 18             | ""
        readFile(2024, 4, "example_3")  | 18             | ""

        readFile(2024, 4, "example_4")  | 5              | ""
        readFile(2024, 4, "example_5")  | 1              | ""
        readFile(2024, 4, "example_6")  | 5              | ""

        readFile(2024, 4, "example_7")  | 11             | ""
        readFile(2024, 4, "example_8")  | 3              | ""
        readFile(2024, 4, "example_9")  | 4              | ""
        readFile(2024, 4, "example_10") | 4              | ""

        readFile(2024, 4)               | 0              | ">446"
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2024, 4) | 0              | ""
    }

}
