package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day07Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day07(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 7, "example_1") | 21             | ""

        readFile(2025, 7)              | 1690           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day07(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult  | comment
        readFile(2025, 7, "test_1")    | 4               | ""
        readFile(2025, 7, "test_2")    | 8               | ""
        readFile(2025, 7, "test_3")    | 5               | ""

        readFile(2025, 7, "example_1") | 40              | ""

        readFile(2025, 7)              | 221371496188107 | ""
    }

}
