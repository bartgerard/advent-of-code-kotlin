package aock2018


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2018Day02Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2018Day02(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2018, 2, "example_1") | 12             | ""

        readFile(2018, 2)              | 5904           | ""
    }

    def "partTwo"() {
        when:
        final String result = new Year2018Day02(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult              | comment
        readFile(2018, 2, "example_2") | "fgij"                      | ""

        readFile(2018, 2)              | "jiwamotgsfrudclzbyzkhlrvp" | ""
    }

}
