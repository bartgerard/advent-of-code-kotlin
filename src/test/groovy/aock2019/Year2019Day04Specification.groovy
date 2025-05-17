package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "111111-111111"   | 1              | ""
        "223450-223450"   | 0              | ""
        "123789-123789"   | 0              | ""

        readFile(2019, 4) | 511            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "112233-112233"   | 1              | ""
        "123444-123444"   | 0              | ""
        "111122-111122"   | 1              | ""

        readFile(2019, 4) | 316            | ""
    }

}
