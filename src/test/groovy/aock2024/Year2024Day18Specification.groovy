package aock2024

import shared.Point2d
import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day18Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day18(start, destination, input).partOne(time)

        then:
        result == expectedResult

        where:
        input                           | start             | destination         | time | expectedResult | comment
        readFile(2024, 18, "example_1") | new Point2d(0, 0) | new Point2d(6, 6)   | 12   | 22             | ""

        readFile(2024, 18)              | new Point2d(0, 0) | new Point2d(70, 70) | 1024 | 356            | ""
    }

    @Ignore("slow")
    def "partTwo"() {
        when:
        final String result = new Year2024Day18(start, destination, input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | start             | destination         | expectedResult | comment
        readFile(2024, 18, "example_1") | new Point2d(0, 0) | new Point2d(6, 6)   | "6,1"          | ""

        readFile(2024, 18)              | new Point2d(0, 0) | new Point2d(70, 70) | "22,33"        | ""
    }

}
