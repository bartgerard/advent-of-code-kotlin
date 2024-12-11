package aock2015

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day09Specification extends Specification {

    def "what is the distance of the shortest route"() {
        when:
        final long result = new Year2015Day09(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2015, 9, "example_1") | 605            | ""

        readFile(2015, 9)              | 207            | ""
    }

    def "what is the distance of the longest route"() {
        when:
        final long result = new Year2015Day09(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2015, 9, "example_1") | 982            | ""

        readFile(2015, 9)              | 804            | ""
    }

}
