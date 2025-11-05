package aock2025

import aock2024.Year2024Day25
import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("template")
class Year2025Day1xSpecification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day1x(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2025, 0) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day1x(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2025, 0) | 0              | ""
    }

}
