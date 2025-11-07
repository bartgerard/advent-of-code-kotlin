package eck.eck2025

import ec2025.Year2025Quest03
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest03Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2025Quest03(input).partOne()

        then:
        result == expectedResult

        where:
        input                                   | expectedResult | comment
        "10,5,1,10,3,8,5,2,2"                   | 29             | ""

        readFile("eck/2025/quest3/input_1.txt") | 2647           | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2025Quest03(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                                                                        | expectedResult | comment
        "4,51,13,64,57,51,82,57,16,88,89,48,32,49,49,2,84,65,49,43,9,13,2,3,75,72,63,48,61,14,40,77" | 781            | ""

        readFile("eck/2025/quest3/input_2.txt")                                                      | 313            | ""
    }

    def "partThree"() {
        when:
        final Long result = new Year2025Quest03(input).partThree()

        then:
        result == expectedResult

        where:
        input                                                                                        | expectedResult | comment
        "4,51,13,64,57,51,82,57,16,88,89,48,32,49,49,2,84,65,49,43,9,13,2,3,75,72,63,48,61,14,40,77" | 3              | ""

        readFile("eck/2025/quest3/input_3.txt")                                                      | 2411           | ""
    }

}
