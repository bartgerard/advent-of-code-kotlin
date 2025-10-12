package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day11Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day11(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 11, "example_1") | 10605          | ""

        readFile(2022, 11)              | 61503          | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2022Day11(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 11, "example_1") | 2713310158     | ""

        readFile(2022, 11)              | 14081365540    | ""
    }

}
