package aock2024

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day14Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day14(width, height, input).partOne(t)

        then:
        result == expectedResult

        where:
        input                           | width | height | t   | expectedResult | comment
        readFile(2024, 14, "example_1") | 11    | 7      | 100 | 12             | ""

        readFile(2024, 14)              | 101   | 103    | 100 | 218619324      | ""
    }

    @Ignore("slow")
    def "partTwo"() {
        when:
        final long result = new Year2024Day14(width, height, input).partTwo()

        then:
        result == expectedResult

        where:
        input              | width | height | expectedResult | comment
        readFile(2024, 14) | 101   | 103    | 6446           | ""
    }

}
