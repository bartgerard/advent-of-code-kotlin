package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2022Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 4, "example_1") | 2              | ""

        readFile(2022, 4)              | 490            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2022Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 4, "example_1") | 4              | ""

        readFile(2022, 4)              | 921            | ""
    }

}
