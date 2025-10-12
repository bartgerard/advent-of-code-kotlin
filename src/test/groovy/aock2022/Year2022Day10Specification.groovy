package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day10Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day10(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 10, "example_0") | 0              | ""
        readFile(2022, 10, "example_1") | 13140          | ""

        readFile(2022, 10)              | 15680          | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2022Day10(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2022, 10, "example_1") | 0              | "???"

        readFile(2022, 10)              | 0              | "ZFBFHGUP" // visual puzzle see output
    }

}
