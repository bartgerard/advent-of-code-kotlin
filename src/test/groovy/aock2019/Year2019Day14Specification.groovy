package aock2019

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day14Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day14(input).partOne(requiredAmountOfFuel)

        then:
        result == expectedResult

        where:
        input                           | requiredAmountOfFuel | expectedResult | comment
        readFile(2019, 14, "example_1") | 1L                   | 31             | ""
        readFile(2019, 14, "example_2") | 1L                   | 165            | ""
        readFile(2019, 14, "example_3") | 1L                   | 13312          | ""
        readFile(2019, 14, "example_4") | 1L                   | 180697         | ""
        readFile(2019, 14, "example_5") | 1L                   | 2210736        | ""

        readFile(2019, 14)              | 1L                   | 443537         | ""

        // EXPERIMENT

        readFile(2019, 14, "example_1") | 2L                   | 62             | "+31"
        readFile(2019, 14, "example_1") | 3L                   | 93             | "+31"
        readFile(2019, 14, "example_1") | 4L                   | 124            | "+31"
        readFile(2019, 14, "example_1") | 5L                   | 145            | "+21 (-10)"
        readFile(2019, 14, "example_1") | 6L                   | 176            | "+31"
        readFile(2019, 14, "example_1") | 7L                   | 207            | "+31"
        readFile(2019, 14, "example_1") | 8L                   | 238            | "+31"
        readFile(2019, 14, "example_1") | 9L                   | 269            | "+31"
        readFile(2019, 14, "example_1") | 10L                  | 290            | "+21 (-10)"

        readFile(2019, 14, "example_2") | 2L                   | 323            | "+158"
        readFile(2019, 14, "example_2") | 3L                   | 480            | "+157"
        readFile(2019, 14, "example_2") | 4L                   | 638            | "+158"
        readFile(2019, 14, "example_2") | 5L                   | 796            | "+158"
        readFile(2019, 14, "example_2") | 6L                   | 953            | "+157"
        readFile(2019, 14, "example_2") | 7L                   | 1111           | "+158"
        readFile(2019, 14, "example_2") | 8L                   | 1276           | "+165"
        readFile(2019, 14, "example_2") | 9L                   | 1426           | "+150"
        readFile(2019, 14, "example_2") | 10L                  | 1584           | "+158"
        readFile(2019, 14, "example_2") | 11L                  | 1749           | "+165"
        readFile(2019, 14, "example_2") | 12L                  | 1899           | "+150"
        readFile(2019, 14, "example_2") | 13L                  | 2064           | "+165"
        readFile(2019, 14, "example_2") | 14L                  | 2222           | "+158"
        readFile(2019, 14, "example_2") | 15L                  | 2372           | "+150"
    }

    @Ignore("slow")
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

        readFile(2019, 14)              | 2910558        | ""
    }

}
