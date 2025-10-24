package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day13Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day13(input).partOne()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        readFile(2019, 13) | 309            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day13(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        readFile(2019, 13) | 0              | ""
    }

}
