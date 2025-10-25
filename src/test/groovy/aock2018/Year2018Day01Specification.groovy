package aock2018


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2018Day01Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2018Day01(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "+1, -2, +3, +1"  | 3              | ""
        "+1, +1, +1"      | 3              | ""
        "+1, +1, -2"      | 0              | ""
        "-1, -2, -3"      | -6             | ""

        readFile(2018, 1) | 435            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2018Day01(input).partTwo()

        then:
        result == expectedResult

        where:
        input                | expectedResult | comment
        "+1, -2, +3, +1"     | 2              | ""
        "+1, -1"             | 0              | ""
        "+3, +3, +4, -2, -4" | 10             | ""
        "-6, +3, +8, +5, -6" | 5              | ""
        "+7, +7, -2, -7, -4" | 14             | ""

        readFile(2018, 1)    | 245            | ""
    }

}
