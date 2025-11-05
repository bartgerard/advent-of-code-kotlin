package aock2017

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2017Day04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2017Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "aa bb cc dd ee"  | 1              | ""
        "aa bb cc dd aa"  | 0              | ""
        "aa bb cc dd aaa" | 1              | ""

        readFile(2017, 4) | 383            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2017Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input                      | expectedResult | comment
        "abcde fghij"              | 1              | ""
        "abcde xyz ecdab"          | 0              | ""
        "a ab abc abd abf abj"     | 1              | ""
        "iiii oiii ooii oooi oooo" | 1              | ""
        "oiii ioii iioi iiio"      | 0              | ""

        readFile(2017, 4)          | 265            | ""
    }

}
