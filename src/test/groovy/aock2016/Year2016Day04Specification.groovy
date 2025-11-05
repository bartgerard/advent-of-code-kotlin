package aock2016


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2016Day04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2016Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        "aaaaa-bbb-z-y-x-123[abxyz]"   | 123            | ""
        "a-b-c-d-e-f-g-h-987[abcde]"   | 987            | ""
        "not-a-real-room-404[oarel]"   | 404            | ""
        "totally-real-room-200[decoy]" | 0              | ""

        readFile(2016, 4)              | 245102         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2016Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        readFile(2016, 4) | 324            | ""
    }

}
