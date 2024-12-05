package aock2015

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day01Specification extends Specification {

    def "to what floor do the instructions take Santa"() {
        when:
        final int floor = new Year2015Day01(input).floor()

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

        readFile(2015, 1) | 138            | ""
    }

    def "what is the position of the character that causes Santa to first enter the basement"() {
        when:
        final int position = new Year2015Day01(input).positionTillFirstArrivalAt(-1)

        then:
        position == expectedResult

        where:
        input             | expectedResult | comment
        ")"               | 1              | ""
        "()())"           | 5              | ""

        readFile(2015, 1) | 1771           | ""
    }

}
