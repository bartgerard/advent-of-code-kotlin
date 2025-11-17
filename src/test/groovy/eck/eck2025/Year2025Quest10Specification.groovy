package eck.eck2025

import ec2025.Year2025Quest10
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest10Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest10(input).partOne(moves)

        then:
        result == expectedResult

        where:
        input                                      | moves || expectedResult | comment
        readFile("eck/2025/quest10/example_1.txt") | 3     || 27             | ""

        readFile("eck/2025/quest10/input_1.txt")   | 4     || 155            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest10(input).partTwo(rounds)

        then:
        result == expectedResult

        where:
        input                                      | rounds || expectedResult | comment
        readFile("eck/2025/quest10/example_2.txt") | 3      || 27             | ""

        readFile("eck/2025/quest10/input_2.txt")   | 20     || 1705           | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest10(input).partThree()

        then:
        result == expectedResult

        where:
        input                                      || expectedResult  | comment
        readFile("eck/2025/quest10/example_3.txt") || 15              | ""
        readFile("eck/2025/quest10/example_4.txt") || 8               | ""
        readFile("eck/2025/quest10/example_5.txt") || 44              | ""
        readFile("eck/2025/quest10/example_6.txt") || 4406            | ""
        readFile("eck/2025/quest10/example_7.txt") || 13033988838     | ""

        //readFile("eck/2025/quest10/input_3.txt")   || 210342294859334 | ""
    }

}
