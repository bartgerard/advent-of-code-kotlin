package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day03Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day03(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 3, "example_1") | 357            | ""

        readFile(2025, 3)              | 17281          | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day03(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult  | comment
        readFile(2025, 3, "example_1") | 3121910778619   | ""

        readFile(2025, 3)              | 171388730430281 | ""
    }

}
