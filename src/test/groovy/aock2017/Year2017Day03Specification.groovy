package aock2017

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2017Day03Specification extends Specification {

    def "how many steps are required to carry the data from the square identified in your puzzle input all the way to the access port"() {
        when:
        final long result = new Year2017Day03(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "2"               | 1              | ""
        "11"              | 2              | ""

        "1"               | 0              | ""
        "12"              | 3              | ""
        "23"              | 2              | ""
        "1024"            | 31             | ""

        "2"               | 1              | ""
        "3"               | 2              | ""
        "4"               | 1              | ""
        "5"               | 2              | ""
        "9"               | 2              | ""

        "11"              | 2              | ""
        "15"              | 2              | ""
        "16"              | 3              | ""
        "25"              | 4              | ""

        readFile(2017, 3) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2017Day03(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "1"               | 0              | ""

        readFile(2017, 3) | 0              | ""
    }

}
