package eck.eck2025

import ec2025.Year2025Quest13
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest13Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest13(input).partOne()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult | comment
        readFile("eck/2025/quest13/example_1.txt") || 67             | ""

        readFile("eck/2025/quest13/input_1.txt")   || 686            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest13(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult | comment
        readFile("eck/2025/quest13/example_2.txt") || 30             | ""

        readFile("eck/2025/quest13/input_2.txt")   || 4578           | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest13(input).partThree()

        then:
        result == expectedResult

        where:
        input                                    || expectedResult | comment
        readFile("eck/2025/quest13/input_3.txt") || 3233           | ""
    }

}
