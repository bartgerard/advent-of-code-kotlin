package aock2020


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2020Day07Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2020Day07(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2020, 7, "example_1") | 4              | ""

        readFile(2020, 7)              | 222            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2020Day07(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2020, 7, "example_1") | 32             | ""
        readFile(2020, 7, "example_2") | 126            | ""

        readFile(2020, 7)              | 13264          | ""
    }

}
