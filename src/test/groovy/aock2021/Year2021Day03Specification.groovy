package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day03Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day03(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2021, 3, "example_1") | 198            | ""

        readFile(2021, 3)              | -1             | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day03(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2021, 3, "example_1") | -1             | ""

        readFile(2021, 3)              | -1             | ""
    }

}
