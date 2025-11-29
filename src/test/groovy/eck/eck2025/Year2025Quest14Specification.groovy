package eck.eck2025

import ec2025.Year2025Quest14
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest14Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest14(input).partOne()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult | comment
        readFile("eck/2025/quest14/example_1.txt") || 200            | ""

        readFile("eck/2025/quest14/input_1.txt")   || 469            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest14(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                    || expectedResult | comment
        readFile("eck/2025/quest14/input_2.txt") || 1170969        | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest14(input).partThree()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult | comment
        readFile("eck/2025/quest14/example_2.txt") || 278388552      | ""

        readFile("eck/2025/quest14/input_3.txt")   || -1             | ""
    }

}
