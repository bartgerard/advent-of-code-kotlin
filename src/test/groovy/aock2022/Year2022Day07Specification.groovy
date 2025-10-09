package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day07Specification extends Specification {

    def "partOne"() {
        when:
        final String result = new Year2022Day07(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 7, "example_1") | 95437          | ""

        readFile(2022, 7)              | 0              | ""
    }

    def "partTwo"() {
        when:
        final String result = new Year2022Day07(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 7, "example_1") | 0              | ""

        readFile(2022, 7)              | 0              | ""
    }

}
