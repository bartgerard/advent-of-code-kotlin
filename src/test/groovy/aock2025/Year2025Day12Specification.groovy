package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day12Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day12(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2025, 12, "example_1") | 2              | "TODO"

        readFile(2025, 12)              | 406            | ""
    }

}
