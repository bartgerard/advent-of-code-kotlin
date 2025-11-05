package aock2016


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2016Day03Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2016Day03(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "5 10 25"         | 0              | ""

        readFile(2016, 3) | 869            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2016Day03(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        readFile(2016, 3) | 1544           | ""
    }

}
