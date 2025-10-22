package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day11Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day11(input).partOne()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        readFile(2019, 11) | -1             | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day11(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        readFile(2019, 11) | -1             | ""
    }

}
