package aock2024

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day01Specification extends Specification {

    def "what is the total distance between your lists"() {
        when:
        final long result = new Year2024Day01(input).distanceBetweenLists()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 1, "example_1") | 11             | ""

        readFile(2024, 1)              | 1603498        | ""
    }

    def "what is their similarity score"() {
        when:
        final long result = new Year2024Day01(input).similarityScore()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 1, "example_1") | 31             | ""

        readFile(2024, 1)              | 25574739       | ""
    }

}
