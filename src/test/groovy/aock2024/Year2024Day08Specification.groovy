package aock2024


import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("template")
class Year2024Day08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day08(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2024, 8) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day08(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2024, 8) | 0              | ""
    }

}
