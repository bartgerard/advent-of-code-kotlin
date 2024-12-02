package aock2024

import spock.lang.Specification

import static aock2024.Year2024Day02Kt.parse2024Day02
import static shared.InputsKt.readFile

class Year2024Day02Specification extends Specification {

    def "how many reports are safe"() {
        when:
        final int result = parse2024Day02(input).countSafe()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2024, 2, "example_1") | 2              | ""

        readFile(2024, 2)              | 510            | ""
    }

    def "how many reports are safe with tolerance"() {
        when:
        final long result = parse2024Day02(input).countSafeWithTolerance()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        "0 3 6 9"                      | 1              | ""
        "0 3 7 6"                      | 1              | ""
        "0 6 6 9"                      | 0              | ""
        "0 3 7 10"                     | 0              | ""
        "0 3 7 11"                     | 0              | ""
        "0 4 6 9"                      | 1              | ""
        "0 4 6 9"                      | 1              | ""
        "0 4 8 11"                     | 0              | ""
        "0 3 3 6"                      | 1              | ""
        "0 3 6 99"                     | 1              | ""
        "57 60 62 64 63 64 65"         | 1              | ""

        readFile(2024, 2, "example_1") | 4              | ""

        readFile(2024, 2)              | 553            | ""
    }

}
