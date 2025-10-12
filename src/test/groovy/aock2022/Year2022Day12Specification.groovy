package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day12Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day12(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 12, "example_1") | 31             | ""

        readFile(2022, 12)              | 383            | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2022Day12(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 12, "example_1") | 29             | ""

        readFile(2022, 12)              | 377            | ""
    }

}
