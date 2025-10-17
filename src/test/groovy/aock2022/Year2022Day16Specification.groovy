package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day16Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day16(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 16, "example_1") | 0              | ""

        readFile(2022, 16)              | 0              | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2022Day16(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 16, "example_1") | 0              | ""

        readFile(2022, 16)              | 0              | ""
    }

}
