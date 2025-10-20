package aock2015

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day13Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2015Day13(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2015, 13, "example_1") | 330            | ""

        readFile(2015, 13)              | 733            | ""
    }

    @Ignore("slow")
    def "partTwo"() {
        when:
        final long result = new Year2015Day13(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        readFile(2015, 13) | 725            | ""
    }

}
