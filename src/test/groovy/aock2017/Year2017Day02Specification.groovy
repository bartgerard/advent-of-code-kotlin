package aock2017

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2017Day02Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2017Day02(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "5 1 9 5"         | 8              | ""
        "7 5 3"           | 4              | ""
        "2 4 6 8"         | 6              | ""

        readFile(2017, 2) | 47623          | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2017Day02(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "5 9 2 8"         | 4              | ""
        "9 4 7 3"         | 3              | ""
        "3 8 6 5"         | 2              | ""

        readFile(2017, 2) | 312            | ""
    }

}
