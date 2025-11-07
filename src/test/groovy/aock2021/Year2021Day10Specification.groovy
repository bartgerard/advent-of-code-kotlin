package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day10Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day10(input).partOne()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 10, "example_1") || 26397          | ""

        readFile(2021, 10)              || 296535         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day10(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 10, "example_1") || 288957         | ""

        readFile(2021, 10)              || 4245130838     | ""
    }

}
