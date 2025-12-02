package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day02Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day02(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 2, "example_1") | 1227775554     | ""

        readFile(2025, 2)              | 40214376723    | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day02(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 2, "example_1") | 4174379265     | ""

        readFile(2025, 2)              | 50793864718    | ""
    }

}
