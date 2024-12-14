package aock2023


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2023Day25Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2023Day25(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2023, 25, "example_1") | 54             | ""

        readFile(2023, 25)              | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2023Day25(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2024, 0) | 0              | ""
    }

}
