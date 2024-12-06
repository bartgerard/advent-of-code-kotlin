package aock2024

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day06Specification extends Specification {

    def "how many distinct positions will the guard visit before leaving the mapped area"() {
        when:
        final long result = new Year2024Day06(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 6, "example_1") | 41             | ""

        readFile(2024, 6)              | 4967           | ""
    }

    @Ignore("slow")
    def "partTwo"() {
        when:
        final long result = new Year2024Day06(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 6, "example_1") | 6              | ""

        readFile(2024, 6)              | 1789           | ""
    }

}
