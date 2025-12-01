package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day01Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day01(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 1, "example_1") | 3              | ""

        readFile(2025, 1)              | 1139           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day01(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 1, "example_1") | 6              | ""

        readFile(2025, 1)              | 6684           | ""
    }

}
