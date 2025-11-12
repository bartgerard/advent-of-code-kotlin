package eck.eck2025

import ec2025.Year2025Quest07
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest07Specification extends Specification {

    def "partOne"() {
        when:
        final String result = new Year2025Quest07(input).partOne()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest7/example_1.txt") | "Oroneth"      | ""

        readFile("eck/2025/quest7/input_1.txt")   | "Azmirath"     | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest07(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest7/example_2.txt") | 23             | ""

        readFile("eck/2025/quest7/input_2.txt")   | 2113           | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest07(input).partThree()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest7/example_3.txt") | 25             | ""
        readFile("eck/2025/quest7/example_4.txt") | 1154           | ""

        readFile("eck/2025/quest7/input_3.txt")   | 2190601        | ""
    }

}
