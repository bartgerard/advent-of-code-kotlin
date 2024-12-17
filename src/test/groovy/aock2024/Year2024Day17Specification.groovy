package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day17Specification extends Specification {

    def "partOne"() {
        when:
        final String result = new Year2024Day17(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult          | comment
        "A=0,B=0,C=9\n\n2,6"            | ""                      | "B=1"
        "A=10,B=0,C=0\n\n5,0,5,1,5,4"   | "0,1,2"                 | ""
        "A=2024,B=0,C=0\n\n0,1,5,4,3,0" | "4,2,5,6,7,7,7,7,3,1,0" | "A=0"
        "A=0,B=29,C=0\n\n1,7"           | ""                      | "B=26"
        "A=0,B=2024,C=43690\n\n4,0"     | ""                      | "B=44354"

        readFile(2024, 17, "example_1") | "4,6,3,5,6,3,5,2,1,0"   | ""

        readFile(2024, 17)              | "4,3,2,6,4,5,3,2,4"     | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day17(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult  | comment
        readFile(2024, 17, "example_2") | 117440          | ""

        readFile(2024, 17)              | 164540892147389 | ""
    }

}
