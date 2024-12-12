package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day12Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day12(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 12, "example_1") | 140            | ""
        readFile(2024, 12, "example_2") | 772            | ""
        readFile(2024, 12, "example_3") | 1930           | ""

        readFile(2024, 12)              | 1573474        | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day12(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 12, "example_1") | 80             | ""
        readFile(2024, 12, "example_2") | 436            | ""
        readFile(2024, 12, "example_3") | 1206           | ""

        readFile(2024, 12)              | 0              | ""
    }

}
