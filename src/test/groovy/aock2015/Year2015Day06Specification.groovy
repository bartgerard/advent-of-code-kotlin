package aock2015


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day06Specification extends Specification {

    def "how many lights are lit"() {
        when:
        final long result = new Year2015Day06(input).partOne()

        then:
        result == expectedResult

        where:
        input                              | expectedResult | comment
        "turn on 0,0 through 2,2"          | 9              | ""

        "turn on 0,0 through 999,999"      | 1000000        | "turn on (or leave on) every light"
        "toggle 0,0 through 999,0"         | 1000           | "toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off"
        "turn off 499,499 through 500,500" | 0              | "turn off (or leave off) the middle four lights"
        "turn on 499,499 through 500,500"  | 4              | "turn on (or leave on) the middle four lights"

        readFile(2015, 6)                  | 377891         | ""
    }

    def "what is the total brightness of all lights combined after following Santa's instructions"() {
        when:
        final long result = new Year2015Day06(input).partTwo()

        then:
        result == expectedResult

        where:
        input                        | expectedResult | comment
        "turn on 0,0 through 0,0"    | 1              | ""
        "toggle 0,0 through 999,999" | 2000000        | ""

        readFile(2015, 6)            | 14110788       | ""
    }

}
