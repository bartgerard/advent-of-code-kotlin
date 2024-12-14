package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day15Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day15(input).partOne()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        ""                 | 0              | ""

        readFile(2024, 15) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day15(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        ""                 | 0              | ""

        readFile(2024, 15) | 0              | ""
    }

}
