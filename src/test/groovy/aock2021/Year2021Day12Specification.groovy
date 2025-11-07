package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day12Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day12(input).partOne()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 12, "example_1") || 10             | ""
        readFile(2021, 12, "example_2") || 19             | ""
        readFile(2021, 12, "example_3") || 226            | ""

        readFile(2021, 12)              || 3298           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day12(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 12, "example_1") || 36             | ""
        readFile(2021, 12, "example_2") || 103            | ""
        readFile(2021, 12, "example_3") || 3509           | ""

        readFile(2021, 12)              || 93572          | ""
    }

}
