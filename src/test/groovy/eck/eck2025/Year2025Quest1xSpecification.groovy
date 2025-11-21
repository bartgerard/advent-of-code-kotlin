package eck.eck2025

import spock.lang.Ignore
import spock.lang.Specification
import templates.Year202xQuest1x

import static shared.InputsKt.readFile

@Ignore("template")
class Year2025Quest1xSpecification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year202xQuest1x(input).partOne()

        then:
        result == expectedResult

        where:
        input                                   || expectedResult | comment
        ""                                      || 0              | ""

        readFile("eck/2025/questX/input_1.txt") || 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year202xQuest1x(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                   || expectedResult | comment
        ""                                      || 0              | ""

        readFile("eck/2025/questX/input_2.txt") || 0              | ""
    }

    def "partThree"() {
        when:
        final long result = new Year202xQuest1x(input).partThree()

        then:
        result == expectedResult

        where:
        input                                   || expectedResult | comment
        ""                                      || 0              | ""

        readFile("eck/2025/questX/input_3.txt") || 0              | ""
    }

}
