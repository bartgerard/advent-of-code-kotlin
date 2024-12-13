package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day13Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day13(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 13, "example_1") | 480            | "280 + 200"

        readFile(2024, 13)              | 28887          | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day13(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 13, "example_1") | 0              | ""

        readFile(2024, 13)              | 0              | "not 288870000000000000"
    }

}
