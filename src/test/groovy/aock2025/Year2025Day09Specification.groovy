package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day09Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day09(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 9, "example_1") | 50             | ""

        readFile(2025, 9)              | 4738108384     | "<>2147268864"
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day09(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 9, "example_1") | 24             | ""

        readFile(2025, 9)              | 0              | "<>4533843095"
    }

}
