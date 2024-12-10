package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day10Specification extends Specification {

    def "what is the sum of the scores of all trailheads on your topographic map"() {
        when:
        final long result = new Year2024Day10(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 10, "example_1") | 1              | ""
        readFile(2024, 10, "example_2") | 2              | ""
        readFile(2024, 10, "example_3") | 4              | ""
        readFile(2024, 10, "example_4") | 3              | ""

        readFile(2024, 10, "example_5") | 36             | ""

        readFile(2024, 10)              | 574            | ""
    }

    def "what is the sum of the ratings of all trailheads"() {
        when:
        final long result = new Year2024Day10(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 10, "example_6") | 3              | ""
        readFile(2024, 10, "example_7") | 13             | ""
        readFile(2024, 10, "example_8") | 227            | ""

        readFile(2024, 10, "example_5") | 81             | ""

        readFile(2024, 10)              | 1238           | ""
    }

}
