package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day05Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day05(input).partOne()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 5, "example_1") || 5              | ""

        readFile(2021, 5)              || 7414           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day05(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 5, "example_1") || 12             | ""

        readFile(2021, 5)              || 19676          | ""
    }

}
