package aock2025


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Day08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Day08(input).partOne(connections)

        then:
        result == expectedResult

        where:
        input                          | connections | expectedResult | comment
        readFile(2025, 8, "example_1") | 10          | 40             | ""

        readFile(2025, 8)              | 1000        | 0              | ">54684"
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Day08(input).partTwo()

        then:
        result == expectedResult

        where:
        input                          | expectedResult | comment
        readFile(2025, 8, "example_1") | 25272          | ""

        readFile(2025, 8)              | 51294528       | ""
    }

}
