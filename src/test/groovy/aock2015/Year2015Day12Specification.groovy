package aock2015


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day12Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2015Day12(input).partOne()

        then:
        result == expectedResult

        where:
        input                        | expectedResult | comment
        "[1,2,3]"                    | 6              | ""
        "{\"a\":2,\"b\":4}"          | 6              | ""
        "[[[3]]]"                    | 3              | ""
        "{\"a\":{\"b\":4},\"c\":-1}" | 3              | ""
        "{\"a\":[-1,1]}"             | 0              | ""
        "[-1,{\"a\":1}]"             | 0              | ""
        "[]"                         | 0              | ""
        "{}"                         | 0              | ""

        readFile(2015, 12)           | 156366         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2015Day12(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                     | expectedResult | comment
        "[1,{\"c\":\"red\",\"b\":2},3]"           | 4              | ""
        "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}" | 0              | ""
        "[1,\"red\",5]"                           | 6              | ""

        readFile(2015, 12)                        | 96852          | ""
    }

}
