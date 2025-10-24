package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day14Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day14(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2019, 14, "example_1") | 31             | ""
        readFile(2019, 14, "example_2") | 165            | ""
        readFile(2019, 14, "example_3") | 13312          | ""
        readFile(2019, 14, "example_4") | 180697         | ""
        readFile(2019, 14, "example_5") | 2210736        | ""

        readFile(2019, 14)              | 443537         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day14(input).partTwo(1_000_000_000_000)

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2019, 14, "example_3") | 82892753       | ""
        readFile(2019, 14, "example_4") | 5586022        | ""
        readFile(2019, 14, "example_5") | 460664         | ""

        readFile(2019, 14)              | 0              | ""
    }

}
