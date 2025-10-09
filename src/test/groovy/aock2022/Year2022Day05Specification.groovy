package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day05Specification extends Specification {

    def "partOne"() {
        when:
        final String result = new Year2022Day05(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 5, "example_1") | "CMZ"          | ""

        readFile(2022, 5)              | "QNHWJVJZW"    | ""
    }

    def "partTwo"() {
        when:
        final String result = new Year2022Day05(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 5, "example_1") | "MCD"          | ""

        readFile(2022, 5)              | "BPCZJLFJW"    | ""
    }

}
