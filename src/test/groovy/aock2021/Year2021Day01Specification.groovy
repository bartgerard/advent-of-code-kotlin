package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day01Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day01(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2021, 1, "example_1") | 7              | ""

        readFile(2021, 1)              | 1195           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day01(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2021, 1, "example_1") | 5              | ""

        readFile(2021, 1)              | 1235           | ""
    }

}
