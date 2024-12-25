package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day25Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day25(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 25, "example_1") | 3              | ""

        readFile(2024, 25)              | 3133           | ""
    }

}
