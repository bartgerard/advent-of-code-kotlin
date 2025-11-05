package eck.eck2025

import ec2025.Year2025Quest01
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest01Specification extends Specification {

    def "partOne"() {
        when:
        final String result = new Year2025Quest01(input).partOne()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest1/example_1.txt") | "Fyrryn"       | ""

        readFile("eck/2025/quest1/input_1.txt")   | "Quorurath"    | ""
    }

    def "partTwo"() {
        when:
        final String result = new Year2025Quest01(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest1/example_1.txt") | "Elarzris"     | ""

        readFile("eck/2025/quest1/input_2.txt")   | "Zarox"        | ""
    }

    def "partThree"() {
        when:
        final String result = new Year2025Quest01(input).partThree()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest1/example_2.txt") | "Drakzyph"     | ""

        readFile("eck/2025/quest1/input_3.txt")   | "Darthel"      | ""
    }

}
