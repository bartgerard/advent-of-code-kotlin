package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day24Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day24(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 24, "example_1") | 4              | ""
        readFile(2024, 24, "example_2") | 2024           | ""

        readFile(2024, 24)              | 55730288838374 | ""
    }

    def "partTwo"() {
        when:
        final String result = new Year2024Day24(input).partTwo(operation, swaps)

        then:
        result == expectedResult

        where:
        input                           | operation | swaps | expectedResult                    | comment
        readFile(2024, 24, "example_3") | "AND"     | 2     | "z00,z01,z02,z05"                 | ""

        readFile(2024, 24)              | "ADD"     | 4     | "fvw,grf,mdb,nwq,wpq,z18,z22,z36" | ""
    }

}
