package eck.eck2025


import ec2025.Year2025Quest02
import shared.ComplexNumber
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest02Specification extends Specification {

    def "partOne"() {
        when:
        final ComplexNumber result = new Year2025Quest02(input).partOne()

        then:
        result == ComplexNumber.parse(expectedResult)

        where:
        input                                   | expectedResult    | comment
        "A=[25,9]"                              | "[357,862]"       | ""

        readFile("eck/2025/quest2/input_1.txt") | "[158523,730384]" | ""
    }

    def "partTwo"() {
        when:
        final Long result = new Year2025Quest02(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                   | expectedResult | comment
        "A=[35300,-64910]"                      | 4076           | ""

        readFile("eck/2025/quest2/input_2.txt") | 559            | ""
    }

    def "partThree"() {
        when:
        final Long result = new Year2025Quest02(input).partThree()

        then:
        result == expectedResult

        where:
        input                                   | expectedResult | comment
        "A=[35300,-64910]"                      | 406954         | ""

        readFile("eck/2025/quest2/input_2.txt") | 52931          | ""
    }

}
