package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day05Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day05(program).partOne(input).diagnosticCode()

        then:
        result == expectedResult

        where:
        program           | input | expectedResult | comment
        "1002,4,3,4,33"   | [1]   | -1             | ""
        "1101,100,-1,4,0" | [1]   | -1             | ""

        readFile(2019, 5) | [1]   | 11933517       | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day05(program).partTwo(input).diagnosticCode()

        then:
        result == expectedResult

        where:
        program                                    | input | expectedResult | comment
        "3,9,8,9,10,9,4,9,99,-1,8"                 | [0]   | 0              | "Using position mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not)."
        "3,9,8,9,10,9,4,9,99,-1,8"                 | [2]   | 0              | "Using position mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not)."
        "3,9,8,9,10,9,4,9,99,-1,8"                 | [8]   | 1              | "Using position mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not)."

        "3,9,7,9,10,9,4,9,99,-1,8"                 | [7]   | 1              | "Using position mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not)."
        "3,9,7,9,10,9,4,9,99,-1,8"                 | [8]   | 0              | "Using position mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not)."

        "3,3,1108,-1,8,3,4,3,99"                   | [9]   | 0              | "Using immediate mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not)."
        "3,3,1108,-1,8,3,4,3,99"                   | [8]   | 1              | "Using immediate mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not)."

        "3,3,1107,-1,8,3,4,3,99"                   | [7]   | 1              | "Using immediate mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not)."
        "3,3,1107,-1,8,3,4,3,99"                   | [8]   | 0              | "Using immediate mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not)."

        "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9" | [0]   | 0              | "(using position mode)"
        "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9" | [1]   | 1              | "(using position mode)"
        "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9" | [2]   | 1              | "(using position mode)"

        "3,3,1105,-1,9,1101,0,0,12,4,12,99,1"      | [0]   | 0              | "(using immediate mode)"
        "3,3,1105,-1,9,1101,0,0,12,4,12,99,1"      | [1]   | 1              | "(using immediate mode)"

        readFile(2019, 5, "example_1")             | [1]   | 999            | "<= 8"
        readFile(2019, 5, "example_1")             | [9]   | 1001           | "> 8"

        readFile(2019, 5)                          | [5]   | 10428568       | ""
    }

}
