package aock2015


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day11Specification extends Specification {

    def "partOne"() {
        when:
        final String result = new Year2015Day11(input).partOne()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        "abcdefgh"         | "abcdffaa"     | ""
        "ghijklmn"         | "ghjaabcc"     | ""

        readFile(2015, 11) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2015Day11(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        ""                 | 0              | ""

        readFile(2015, 11) | 0              | ""
    }

}
