package aock2023

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2023Day21Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2023Day21(input).partOne(steps)

        then:
        result == expectedResult

        where:
        input                           | steps | expectedResult | comment
        readFile(2023, 21, "example_1") | 6     | 16             | ""

        readFile(2023, 21)              | 64    | 3768           | ""
    }

    @Ignore("slow")
    def "partTwo"() {
        when:
        final long result = new Year2023Day21(input).partTwo(steps)

        then:
        result == expectedResult

        where:
        input                           | steps | expectedResult | comment
        readFile(2023, 21, "example_1") | 6     | 16             | ""
        readFile(2023, 21, "example_1") | 10    | 50             | ""
        readFile(2023, 21, "example_1") | 50    | 1594           | ""
        readFile(2023, 21, "example_1") | 100   | 6536           | ""
        readFile(2023, 21, "example_1") | 500   | 167004         | ""
        readFile(2023, 21, "example_1") | 1000  | 668697         | ""
        readFile(2023, 21, "example_1") | 5000  | 16733044       | ""
    }

    def "partTwoCyclic"() {
        when:
        final long result = new Year2023Day21(input).partTwoCyclic(steps)

        then:
        result == expectedResult

        where:
        input              | steps    | expectedResult  | comment
        readFile(2023, 21) | 26501365 | 627960775905777 | "26501365 = 202300 * 131 + 65"
    }

}
