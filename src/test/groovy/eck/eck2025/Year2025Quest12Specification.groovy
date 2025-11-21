package eck.eck2025

import ec2025.Year2025Quest12
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest12Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest12(input).partOne()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult | comment
        readFile("eck/2025/quest12/example_1.txt") || 16             | ""

        readFile("eck/2025/quest12/input_1.txt")   || 236            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest12(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult | comment
        readFile("eck/2025/quest12/example_2.txt") || 58             | ""

        readFile("eck/2025/quest12/input_2.txt")   || 5617           | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest12(input).partThree()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult | comment
        readFile("eck/2025/quest12/example_3.txt") || 14             | ""
        readFile("eck/2025/quest12/example_4.txt") || 136            | ""

        readFile("eck/2025/quest12/input_3.txt")   || 3970           | ""
    }

}
