package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day06Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day06(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 6, "example_1") | 4277556        | ""

        readFile(2025, 6)              | 3261038365331  | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day06(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 6, "example_1") | 3263827        | ""

        readFile(2025, 6)              | 8342588849093  | ""
    }

}
