package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day09Specification extends Specification {

    def "what is the resulting filesystem checksum"() {
        when:
        final long result = new Year2024Day09(input).partOne()

        then:
        result == expectedResult

        where:
        input                 | expectedResult | comment
        "2333133121414131402" | 1928           | ""

        readFile(2024, 9)     | 6242766523059  | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day09(input).partTwo()

        then:
        result == expectedResult

        where:
        input                 | expectedResult | comment
        "2333133121414131402" | 2858           | ""

        readFile(2024, 9)     | 6272188244509  | ""
    }

}
