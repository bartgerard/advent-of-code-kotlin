package aock2015

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day07Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2015Day07(input).partOne(wire)

        then:
        result == expectedResult

        where:
        input                          | wire | expectedResult | comment
        readFile(2015, 7, "example_1") | "d"  | 72             | ""
        readFile(2015, 7, "example_1") | "e"  | 507            | ""
        readFile(2015, 7, "example_1") | "f"  | 492            | ""
        readFile(2015, 7, "example_1") | "g"  | 114            | ""
        readFile(2015, 7, "example_1") | "h"  | 65412          | ""
        readFile(2015, 7, "example_1") | "i"  | 65079          | ""
        readFile(2015, 7, "example_1") | "x"  | 123            | ""
        readFile(2015, 7, "example_1") | "y"  | 456            | ""

        readFile(2015, 7)              | "a"  | 956            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2015Day07(input).partTwo(wire)

        then:
        result == expectedResult

        where:
        input             | wire | expectedResult | comment
        readFile(2015, 7) | "a"  | 40149          | ""
    }

}
