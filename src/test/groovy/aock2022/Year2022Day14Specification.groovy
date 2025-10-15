package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day14Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day14(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 14, "example_1") | 24             | ""

        readFile(2022, 14)              | 838            | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2022Day14(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 14, "example_1") | 0              | ""

        readFile(2022, 14)              | 0              | ""
    }

}
