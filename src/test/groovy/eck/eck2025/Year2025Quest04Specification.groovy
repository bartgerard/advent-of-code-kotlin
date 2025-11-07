package eck.eck2025

import ec2025.Year2025Quest04
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest04(input).partOne()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest4/example_1.txt") | 32400          | ""
        readFile("eck/2025/quest4/example_2.txt") | 15888          | ""

        readFile("eck/2025/quest4/input_1.txt")   | 10828          | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest04(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest4/example_1.txt") | 625000000000   | ""
        readFile("eck/2025/quest4/example_2.txt") | 1274509803922  | ""

        readFile("eck/2025/quest4/input_2.txt")   | 1983805668017  | ""
    }

    def "partThree"() {
        when:
        final double result = new Year2025Quest04(input).partThree()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest4/example_3.txt") | 400            | ""
        readFile("eck/2025/quest4/example_4.txt") | 6818           | ""

        readFile("eck/2025/quest4/input_3.txt")   | 605974204130   | ""
    }

}
