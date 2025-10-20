package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day10Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day10(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2019, 10, "example_1") | 8              | "(3,4)"
        readFile(2019, 10, "example_2") | 33             | "(5,8)"
        readFile(2019, 10, "example_3") | 35             | "(1,2)"
        readFile(2019, 10, "example_4") | 41             | "(6,3)"
        readFile(2019, 10, "example_5") | 210            | "(11,13)"

        readFile(2019, 10)              | -1              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day10(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2019, 10, "example_1") | 0              | ""

        readFile(2019, 10)              | 0              | ""
    }

}
