package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day06Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day06(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2019, 6, "example_1") | 42             | ""

        readFile(2019, 6)              | 122782         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day06(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2019, 6, "example_2") | 4              | ""

        readFile(2019, 6)              | 271            | ""
    }

}
