package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day01Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2022Day01(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 1, "example_1") | 24000          | ""

        readFile(2022, 1)              | 71124          | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2022Day01(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 1, "example_1") | 45000          | ""

        readFile(2022, 1)              | 204639         | ""
    }

}
