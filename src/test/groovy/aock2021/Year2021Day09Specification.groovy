package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day09Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day09(input).partOne()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 9, "example_1") || 15             | ""

        readFile(2021, 9)              || 566            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day09(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 9, "example_1") || 1134           | ""

        readFile(2021, 9)              || 891684         | ""
    }

}
