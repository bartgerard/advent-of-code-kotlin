package eck.eck2025

import ec2025.Year2025Quest05
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest05Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest05(input).partOne()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest5/example_1.txt") | 581078         | ""

        readFile("eck/2025/quest5/input_1.txt")   | 4864764537     | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest05(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest5/example_2.txt") | 77053          | ""

        readFile("eck/2025/quest5/input_2.txt")   | 8507558316202  | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest05(input).partThree()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        readFile("eck/2025/quest5/example_3.txt") | 260            | ""
        readFile("eck/2025/quest5/example_4.txt") | 4              | ""

        readFile("eck/2025/quest5/input_3.txt")   | 31584685       | ""
    }

}
