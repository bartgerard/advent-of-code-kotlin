package aock2022

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day15Specification extends Specification {

    def "partOne"() {
        when:
        final Long result = new Year2022Day15(input).partOne(y)

        then:
        result == expectedResult

        where:
        input                           | y       | expectedResult | comment
        readFile(2022, 15, "example_1") | 10      | 26             | ""

        readFile(2022, 15)              | 2000000 | 4582667        | ""
    }

    @Ignore("slow")
    def "partTwo"() {
        when:
        final Long result = new Year2022Day15(input).partTwo(max)

        then:
        result == expectedResult

        where:
        input                           | max     | expectedResult | comment
        readFile(2022, 15, "example_1") | 20      | 56000011       | "x=14, y=11"

        readFile(2022, 15)              | 4000000 | 10961118625406 | ""
    }

}
