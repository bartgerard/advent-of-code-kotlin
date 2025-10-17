package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day17Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day17(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 17, "example_1") | 0              | ""

        readFile(2022, 17)              | 0              | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2022Day17(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 17, "example_1") | 0              | ""

        readFile(2022, 17)              | 0              | ""
    }

}
