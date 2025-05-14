package aock2018


import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("template")
class Year2018Day1xSpecification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2018Day1x(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2018, 0) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2018Day1x(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2018, 0) | 0              | ""
    }

}
