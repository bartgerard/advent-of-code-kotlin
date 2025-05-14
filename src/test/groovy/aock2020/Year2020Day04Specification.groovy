package aock2020


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2020Day04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2020Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2020, 4, "example_1") | 2              | ""

        readFile(2020, 4)              | 192            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2020Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2020, 4, "example_1") | 2              | ""
        readFile(2020, 4, "example_2") | 0              | ""
        readFile(2020, 4, "example_3") | 4              | ""

        readFile(2020, 4)              | 101            | ""
    }

}
