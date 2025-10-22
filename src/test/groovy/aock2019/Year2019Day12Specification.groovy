package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day12Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day12(input).partOne(steps)

        then:
        result == expectedResult

        where:
        input                           | steps | expectedResult | comment
        readFile(2019, 12, "example_1") | 10    | 179            | ""
        readFile(2019, 12, "example_2") | 100   | 1940           | ""

        readFile(2019, 12)              | 1000  | 9743           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day12(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult  | comment
        readFile(2019, 12, "example_1") | 2772            | ""
        readFile(2019, 12, "example_2") | 4686774924      | ""

        readFile(2019, 12)              | 288684633706728 | ""
    }

}
