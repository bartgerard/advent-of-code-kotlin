package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day10Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day10(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2025, 10, "example_1") | 7              | ""

        readFile(2025, 10)              | 469            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day10(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        ""                 | 0              | ""

        readFile(2025, 10) | 0              | ""
    }

}
