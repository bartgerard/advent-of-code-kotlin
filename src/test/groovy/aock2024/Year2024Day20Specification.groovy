package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day20Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day20(input).partOne(minimumSaving, 2)

        then:
        result == expectedResult

        where:
        input                           | minimumSaving | expectedResult | comment
        readFile(2024, 20, "example_1") | 0             | 44             | ""

        readFile(2024, 20)              | 100           | 1310           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day20(input).partOne(minimumSaving, 20)

        then:
        result == expectedResult

        where:
        input                           | minimumSaving | expectedResult | comment
        readFile(2024, 20, "example_1") | 50            | 285            | ""

        readFile(2024, 20)              | 100           | 0              | "not 7704580, 982858"
    }

}
