package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day03Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2022Day03(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 3, "example_1") | 157            | ""

        readFile(2022, 3)              | 8053           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2022Day03(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 3, "example_1") | 70             | ""

        readFile(2022, 3)              | 2425           | ""
    }

}
