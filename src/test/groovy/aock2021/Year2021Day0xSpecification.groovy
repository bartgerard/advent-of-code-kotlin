package aock2021


import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("template")
class Year2021Day0xSpecification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day0x(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2021, 0) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day0x(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2021, 0) | 0              | ""
    }

}
