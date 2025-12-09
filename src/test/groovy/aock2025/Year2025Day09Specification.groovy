package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day09Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day09(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 9, "example_1") | 50             | ""

        readFile(2025, 9)              | 4738108384     | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day09(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 9, "example_1") | 24             | ""
        readFile(2025, 9, "test_1")    | 10             | ""
        readFile(2025, 9, "test_2")    | 12             | ""
        readFile(2025, 9, "test_3")    | 16             | ""
        readFile(2025, 9, "test_4")    | 8              | ""

        readFile(2025, 9)              | 1513792010     | ""
    }

}
