package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day05Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day05(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "1002,4,3,4,33"   | 0              | ""

        readFile(2019, 5) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day05(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2019, 5) | 0              | ""
    }

}
