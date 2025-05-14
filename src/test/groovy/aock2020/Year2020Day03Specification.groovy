package aock2020


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2020Day03Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2020Day03(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2020, 3, "example_1") | 7              | ""

        readFile(2020, 3)              | 214            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2020Day03(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2020, 3, "example_1") | 336            | ""

        readFile(2020, 3)              | 8336352024     | ""
    }

}
