package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 4, "example_1") || 4512           | ""

        readFile(2021, 4)              || 16716          | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 4, "example_1") || 1924           | ""

        readFile(2021, 4)              || 4880           | ""
    }

}
