package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day07Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day07(input).partOne()

        then:
        result == expectedResult

        where:
        input                   || expectedResult | comment
        "16,1,2,0,4,2,7,1,2,14" || 37             | ""

        readFile(2021, 7)       || 340052         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day07(input).partTwo()

        then:
        result == expectedResult

        where:
        input                   || expectedResult | comment
        "16,1,2,0,4,2,7,1,2,14" || 168            | ""

        readFile(2021, 7)       || 92948968       | ""
    }

}
