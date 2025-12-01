package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day02Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day02(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2025, 2) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day02(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2025, 2) | 0              | ""
    }

}
