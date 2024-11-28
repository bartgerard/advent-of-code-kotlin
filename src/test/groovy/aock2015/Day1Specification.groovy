package aock2015

import spock.lang.Specification

import static shared.InputsKt.readFileForDay

class Day1Specification extends Specification {

    def "to what floor do the instructions take Santa"() {
        when:
        final int floor = new Day1(input).floor()

        then:
        floor == expectedResult

        where:
        input             | expectedResult | comment
        "(())"            | 0              | ""
        "()()"            | 0              | ""
        "((("             | 3              | ""
        "(()(()("         | 3              | ""
        "))((((("         | 3              | ""
        "())"             | -1             | ""
        "))("             | -1             | ""
        ")))"             | -3             | ""
        ")())())"         | -3             | ""

        readFileForDay(1) | 138            | ""
    }

    def "what is the position of the character that causes Santa to first enter the basement"() {
        when:
        final int position = new Day1(input).positionTillFirstArrivalAt(-1)

        then:
        position == expectedResult

        where:
        input             | expectedResult | comment
        ")"               | 1              | ""
        "()())"           | 5              | ""

        readFileForDay(1) | 1771           | ""
    }

}
