package aock2016


import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("template")
class Year2016Day03Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2016Day03(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2016, 3) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2016Day03(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2016, 3) | 0              | ""
    }

}
