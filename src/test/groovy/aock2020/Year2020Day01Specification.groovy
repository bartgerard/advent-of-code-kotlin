package aock2020


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2020Day01Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2020Day01(input).partOne(2020)

        then:
        result == expectedResult

        where:
        input                            | expectedResult | comment
        "1721\n979\n366\n299\n675\n1456" | 514579         | ""

        readFile(2020, 1)                | 806656         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2020Day01(input).partTwo(2020)

        then:
        result == expectedResult

        where:
        input                            | expectedResult | comment
        "1721\n979\n366\n299\n675\n1456" | 241861950      | ""

        readFile(2020, 1)                | 230608320      | ""
    }

}
