package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day20Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day20(input).partOne(minimumSaving)

        then:
        result == expectedResult

        where:
        input                           | minimumSaving | expectedResult | comment
        readFile(2024, 20, "example_1") | 0             | 44             | ""

        readFile(2024, 20)              | 100           | 0              | "not 6963"
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day20(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        ""                 | 0              | ""

        readFile(2024, 20) | 0              | ""
    }

}
