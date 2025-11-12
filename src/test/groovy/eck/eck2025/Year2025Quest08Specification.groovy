package eck.eck2025

import ec2025.Year2025Quest08
import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("template")
class Year2025Quest08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest08(input).partOne()

        then:
        result == expectedResult

        where:
        input                                   | expectedResult | comment
        ""                                      | 0              | ""

        readFile("eck/2025/quest8/input_1.txt") | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest08(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                   | expectedResult | comment
        ""                                      | 0              | ""

        readFile("eck/2025/quest8/input_2.txt") | 0              | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest08(input).partThree()

        then:
        result == expectedResult

        where:
        input                                   | expectedResult | comment
        ""                                      | 0              | ""

        readFile("eck/2025/quest8/input_3.txt") | 0              | ""
    }

}
