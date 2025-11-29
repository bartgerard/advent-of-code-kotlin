package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day01Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day01(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2025, 1) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day01(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2025, 1) | 0              | ""
    }

}
