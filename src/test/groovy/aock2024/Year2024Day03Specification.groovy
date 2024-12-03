package aock2024

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day03Specification extends Specification {

    def "what do you get if you add up all of the results of the multiplications"() {
        when:
        final long result = new Year2024Day03(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "mul(44,46)"      | 44 * 46        | ""
        "mul(123,4)"      | 123 * 4        | ""

        "mul(4*"          | 0              | "invalid"
        "mul(6,9!"        | 0              | "invalid"
        "?(12,34)"        | 0              | "invalid"
        "mul ( 2 , 4 )"   | 0              | "invalid"

        readFile(2024, 3) | 165225049      | ""
    }

    def "what do you get if you add up all of the results of just the enabled multiplications"() {
        when:
        final long result = new Year2024Day03(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                                                       | expectedResult | comment
        "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))" | 2 * 4 + 8 * 5  | ""

        readFile(2024, 3)                                                           | 108830766      | ""
    }

}
