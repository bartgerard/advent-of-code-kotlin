package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day01Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day01(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "12"              | 2              | ""
        "14"              | 2              | ""
        "1969"            | 654            | ""
        "100756"          | 33583          | ""

        readFile(2019, 1) | 3318632        | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day01(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "14"              | 2              | ""
        "1969"            | 966            | ""
        "100756"          | 50346          | ""

        readFile(2019, 1) | 0              | ""
    }

}
