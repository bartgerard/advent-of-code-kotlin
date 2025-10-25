package aock2016


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2016Day01Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2016Day01(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "R2, L3"          | 5              | ""
        "R2, R2, R2"      | 2              | ""
        "R5, L5, R5, R3"  | 12             | ""

        readFile(2016, 1) | 300            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2016Day01(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "R8, R4, R4, R8"  | 4              | ""

        readFile(2016, 1) | 159            | ""
    }

}
