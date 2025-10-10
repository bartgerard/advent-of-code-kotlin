package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day09Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day09(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 9, "example_1") | 13             | ""

        readFile(2022, 9)              | 6023           | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2022Day09(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 9, "example_1") | 0              | ""

        readFile(2022, 9)              | 0              | ""
    }

}
