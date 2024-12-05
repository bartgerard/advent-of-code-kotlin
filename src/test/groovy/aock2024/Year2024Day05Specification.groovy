package aock2024

import spock.lang.Specification

import static aock2024.Year2024Day05Kt.parseYear2024Day05
import static shared.InputsKt.readFile

class Year2024Day05Specification extends Specification {

    def "partOne"() {
        when:
        final long result = parseYear2024Day05(input).partOne()
        // final long result = new Year2024Day05(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 5, "example_1") | 143            | ""

        readFile(2024, 5)              | 7307           | ""
    }

    def "partTwo"() {
        when:
        final long result = parseYear2024Day05(input).partTwo()
        //final long result = new Year2024Day05(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 5, "example_1") | 123            | ""

        readFile(2024, 5)              | 4713           | ""
    }

}
