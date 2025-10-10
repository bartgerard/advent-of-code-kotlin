package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day08Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day08(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 8, "example_1") | 21             | ""

        readFile(2022, 8)              | 1805           | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2022Day08(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 8, "example_1") | 8              | ""

        readFile(2022, 8)              | 444528         | ""
    }

}
