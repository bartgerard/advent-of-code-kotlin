package aock2021


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2021Day16Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2021Day16(input).partOne()

        then:
        result == expectedResult

        where:
        input                            || expectedResult | comment
        "D2FE28"                         || 2021           | ""
        "EE00D40C823060"                 || 16             | ""
        "8A004A801A8002F478"             || 16             | ""
        "620080001611562C8802118E34"     || 12             | ""
        "C0015000016115A2E0802F182340"   || 23             | ""
        "A0016C880162017C3686B18A3D4780" || 31             | ""

        readFile(2021, 16)               || -1             | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2021Day16(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           || expectedResult | comment
        readFile(2021, 16, "example_1") || -1             | ""

        readFile(2021, 16)              || -1             | ""
    }

}
