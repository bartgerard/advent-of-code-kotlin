package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day08(input).partOne()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 8, "example_1") || 0              | ""
        readFile(2021, 8, "example_2") || 26             | ""

        readFile(2021, 8)              || 381            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day08(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 8, "example_1") || 5353           | ""
        readFile(2021, 8, "example_2") || 61229          | ""

        readFile(2021, 8)              || 1023686        | ""
    }

}
