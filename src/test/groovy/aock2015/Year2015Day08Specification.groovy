package aock2015

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2015Day08(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        "\"\""                         | 2              | ""
        "\"abc\""                      | 2              | ""
        "\"aaa\\\"aaa\""               | 3              | ""
        "\"\\x27\""                    | 5              | ""

        readFile(2015, 8, "example_1") | 12             | ""

        readFile(2015, 8)              | 1333           | ">735"
    }

    def "partTwo"() {
        when:
        final long result = new Year2015Day08(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        "\"\""                         | 4              | ""
        "\"abc\""                      | 4              | ""
        "\"aaa\\\"aaa\""               | 6              | ""
        "\"\\x27\""                    | 5              | ""

        readFile(2015, 8, "example_1") | 19             | ""

        readFile(2015, 8)              | 2046           | ""
    }

}
