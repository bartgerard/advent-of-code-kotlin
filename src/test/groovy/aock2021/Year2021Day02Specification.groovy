package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day02Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day02(input).partOne()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 2, "example_1") || 150            | ""

        readFile(2021, 2)              || 1654760        | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day02(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 2, "example_1") || 900            | ""

        readFile(2021, 2)              || 1956047400     | ""
    }

}
