package aock2020


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2020Day02Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2020Day02(input).partOne()

        then:
        result == expectedResult

        where:
        input                                          | expectedResult | comment
        "1-3 a: abcde\n1-3 b: cdefg\n2-9 c: ccccccccc" | 2              | ""

        readFile(2020, 2)                              | 580            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2020Day02(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                          | expectedResult | comment
        "1-3 a: abcde\n1-3 b: cdefg\n2-9 c: ccccccccc" | 1              | ""

        readFile(2020, 2)                              | 611            | ""
    }

}
