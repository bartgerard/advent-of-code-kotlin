package eck.eck2025

import ec2025.Year2025Quest08
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest08(input).partOne(nails)

        then:
        result == expectedResult

        where:
        input                                   | nails || expectedResult | comment
        "1,5,2,6,8,4,1,7,3"                     | 8     || 4              | ""

        readFile("eck/2025/quest8/input_1.txt") | 32    || 53             | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest08(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                   | nails || expectedResult | comment
        "1,5,2,6,8,4,1,7,3,5,7,8,2"             | 8     || 21             | ""

        readFile("eck/2025/quest8/input_2.txt") | 256   || 2926161        | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest08(input).partThree(nails)

        then:
        result == expectedResult

        where:
        input                                   | nails || expectedResult | comment
        "1,5,2,6,8,4,1,7,3,6"                   | 8     || 7              | ""

        readFile("eck/2025/quest8/input_3.txt") | 256   || 2797           | ""
    }

}
