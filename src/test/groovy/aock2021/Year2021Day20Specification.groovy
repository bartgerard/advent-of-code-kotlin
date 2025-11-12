package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day20Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day20(input).partOne()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 20, "example_1") || -1             | ""

        readFile(2021, 20)              || 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day20(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 20, "example_1") || -1             | ""

        readFile(2021, 20)              || 0              | ""
    }

}
