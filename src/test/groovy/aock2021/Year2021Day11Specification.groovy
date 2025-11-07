package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day11Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day11(input).partOne()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 11, "example_1") || 1656           | ""

        readFile(2021, 11)              || 1705           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day11(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 11, "example_1") || 195            | ""

        readFile(2021, 11)              || 265            | ""
    }

}
