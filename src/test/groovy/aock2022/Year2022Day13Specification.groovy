package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day13Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day13(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 13, "example_1") | 13             | ""

        readFile(2022, 13)              | 6101           | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2022Day13(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 13, "example_1") | 140            | ""

        readFile(2022, 13)              | 21909          | ""
    }

}
