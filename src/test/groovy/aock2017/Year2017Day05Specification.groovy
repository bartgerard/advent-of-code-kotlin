package aock2017

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2017Day05Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2017Day05(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "0\n3\n0\n1\n-3"  | 5              | ""

        readFile(2017, 5) | 394829         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2017Day05(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "0\n3\n0\n1\n-3"  | 10             | ""

        readFile(2017, 5) | 31150702       | ""
    }

}
