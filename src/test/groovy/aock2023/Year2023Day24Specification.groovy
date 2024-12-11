package aock2023


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2023Day24Specification extends Specification {

    def "how many of these intersections occur within the test area"() {
        when:
        final long result = new Year2023Day24(input).to2d().partOne(testArea[0], testArea[1])

        then:
        result == expectedResult

        where:
        input                           | testArea                           | expectedResult | comment
        readFile(2023, 24, "example_1") | [7, 27]                            | 2              | ""

        readFile(2023, 24)              | [200000000000000, 400000000000000] | 20963          | ""
    }

    def "what do you get if you add up the X, Y, and Z coordinates of that initial position"() {
        when:
        final long result = new Year2023Day24(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult  | comment
        readFile(2023, 24, "example_1") | 47              | ""

        readFile(2023, 24)              | 999782576459892 | "x = 287430900705823, y = 451620998712421, z = 260730677041648"
    }

}
