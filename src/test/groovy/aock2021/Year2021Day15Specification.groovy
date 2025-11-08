package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day15Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day15(input).partOne()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 15, "example_1") || -1             | ""

        readFile(2021, 15)              || -1             | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day15(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 15, "example_1") || -1             | ""

        readFile(2021, 15)              || -1             | ""
    }

}
