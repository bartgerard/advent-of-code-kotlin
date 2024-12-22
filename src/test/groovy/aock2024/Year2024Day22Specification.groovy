package aock2024

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day22Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day22(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 22, "example_1") | 37327623       | ""

        readFile(2024, 22)              | 13429191512    | ""
    }

    @Ignore("slow")
    def "partTwo"() {
        when:
        final long result = new Year2024Day22(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 22, "example_2") | 23             | ""

        readFile(2024, 22)              | 1582           | ""
    }

}
