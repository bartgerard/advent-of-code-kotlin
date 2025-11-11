package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day18Specification extends Specification {

    def "snailfish balance"() {
        given:
        SnailfishNumber snailfishNumber = SnailfishNumber.parse(number)
        SnailfishNumber expectedSnailfishNumber = SnailfishNumber.parse(expectedResult)

        when:
        final result = snailfishNumber.balance()

        then:
        result == expectedSnailfishNumber

        where:
        number                                  || expectedResult                  | comment
        "[[[[[9,8],1],2],3],4]"                 || "[[[[0,9],2],3],4]"             | ""
        "[[[[0,9],2],3],4]"                     || "[7,[6,[5,[7,0]]]]"             | ""
        "[[6,[5,[4,[3,2]]]],1]"                 || "[[6,[5,[7,0]]],3]"             | ""
        "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]" || "[[3,[2,[8,0]]],[9,[5,[7,0]]]]" | ""
    }

    def "partOne"() {
        when:
        final long result = new Year2021Day18(input).partOne()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 18, "example_1") || 4140           | ""

        readFile(2021, 18)              || -1             | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day18(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 18, "example_1") || -1             | ""

        readFile(2021, 18)              || -1             | ""
    }

}
