package aock2023


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2023Day21Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2023Day21(input).partOne()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        ""                 | 0              | ""

        readFile(2023, 21) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2023Day21(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        ""                 | 0              | ""

        readFile(2023, 21) | 0              | ""
    }

}
