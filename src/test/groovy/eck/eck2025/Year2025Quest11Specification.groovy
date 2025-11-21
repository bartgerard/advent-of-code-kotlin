package eck.eck2025

import ec2025.Year2025Quest11
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest11Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest11(input).partOne()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult | comment
        readFile("eck/2025/quest11/example_1.txt") || 109            | ""

        readFile("eck/2025/quest11/input_1.txt")   || 248            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest11(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult | comment
        readFile("eck/2025/quest11/example_1.txt") || 11             | ""
        readFile("eck/2025/quest11/example_2.txt") || 1579           | ""

        //readFile("eck/2025/quest11/input_2.txt")   || 3363945        | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest11(input).partThree()

        then:
        result == expectedResult

        where:
        input                                    || expectedResult  | comment
        readFile("eck/2025/quest11/input_3.txt") || 138625711633805 | ""
    }

}
