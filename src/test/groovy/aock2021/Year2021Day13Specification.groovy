package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day13Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day13(input).partOne()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 13, "example_1") || 17             | ""

        readFile(2021, 13)              || 753            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day13(input).partTwo()

        then:
        result == expectedResult

        where:
        input              || expectedResult | comment
        readFile(2021, 13) || 0              | "visual -> HZLEHJRK"
    }

}
