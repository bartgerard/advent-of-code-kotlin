package aock2017

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2017Day01Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2017Day01(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "1122"            | 3              | ""
        "1111"            | 4              | ""
        "1234"            | 0              | ""
        "91212129"        | 9              | ""

        readFile(2017, 1) | 1228           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2017Day01(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "1212"            | 6              | ""
        "1221"            | 0              | ""
        "123425"          | 4              | ""
        "123123"          | 12             | ""
        "12131415"        | 4              | ""

        readFile(2017, 1) | 1238           | ""
    }

}
