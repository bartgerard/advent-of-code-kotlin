package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day02Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2022Day02(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 2, "example_1") | 15             | ""

        readFile(2022, 2)              | 12645          | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2022Day02(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2022, 2, "example_1") | 12             | ""

        readFile(2022, 2)              | 11756          | ""
    }

}
