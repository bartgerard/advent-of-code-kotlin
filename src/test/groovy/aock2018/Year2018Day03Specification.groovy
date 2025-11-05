package aock2018


import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("template")
class Year2018Day03Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2018Day01(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2018, 0) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2018Day01(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2018, 0) | 0              | ""
    }

}
