package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day06Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day06(input).partOne(days)

        then:
        result == expectedResult

        where:
        input             | days || expectedResult | comment
        "3,4,3,1,2"       | 18   || 26             | ""
        "3,4,3,1,2"       | 80   || 5934           | ""

        readFile(2021, 6) | 80   || 386640         | ""

        "3,4,3,1,2"       | 256  || 26984457539    | "partTwo"
        readFile(2021, 6) | 256  || 1733403626279  | "partTwo"
    }

}
