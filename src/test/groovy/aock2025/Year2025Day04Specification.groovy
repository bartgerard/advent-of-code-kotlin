package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 4, "example_1") | 13             | ""

        readFile(2025, 4)              | 1560           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 4, "example_1") | 43             | ""

        readFile(2025, 4)              | 9609           | ""
    }

}
