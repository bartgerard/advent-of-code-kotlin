package aock2021

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore
class Year2021Day06Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day06(input).partOne()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 6, "example_1") || -1             | ""

        readFile(2021, 6)              || -1             | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day06(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          || expectedResult | comment
        readFile(2021, 6, "example_1") || -1             | ""

        readFile(2021, 6)              || -1             | ""
    }

}
