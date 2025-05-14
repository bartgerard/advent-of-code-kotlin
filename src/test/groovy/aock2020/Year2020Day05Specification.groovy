package aock2020


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2020Day05Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2020Day05(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "FBFBBFFRLR"      | 357            | "44 * 8 + 5"
        "BFFFBBFRRR"      | 567            | "row 70, column 7"
        "FFFBBBFRRR"      | 119            | "row 14, column 7"
        "BBFFBBFRLL"      | 820            | "row 102, column 4"

        readFile(2020, 5) | 935            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2020Day05(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        readFile(2020, 5) | 743            | ""
    }

}
