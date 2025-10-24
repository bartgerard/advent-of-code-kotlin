package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day24Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day24(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2019, 24, "example_1") | 2129920        | ""

        readFile(2019, 24)              | 32511025       | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day24(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2019, 24, "example_1") | -1             | ""

        readFile(2019, 24)              | -1             | ""
    }

}
