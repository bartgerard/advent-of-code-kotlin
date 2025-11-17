package eck.eck2025

import ec2025.Year2025Quest09
import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest09Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest09(input).partOne()

        then:
        result == expectedResult

        where:
        input                                     || expectedResult | comment
        readFile("eck/2025/quest9/example_1.txt") || 414            | ""

        readFile("eck/2025/quest9/input_1.txt")   || 5772           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest09(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                     || expectedResult | comment
        readFile("eck/2025/quest9/example_2.txt") || 1245           | ""
    }

    @Ignore("slow")
    def "partTwo slow"() {
        when:
        final long result = new Year2025Quest09(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                   || expectedResult | comment
        readFile("eck/2025/quest9/input_2.txt") || 311048         | ""
    }

    def "partThree"() {
        when:
        final long result = new Year2025Quest09(input).partThree()

        then:
        result == expectedResult

        where:
        input                                     || expectedResult | comment
        readFile("eck/2025/quest9/example_2.txt") || 12             | ""
        readFile("eck/2025/quest9/example_3.txt") || 36             | ""
    }

    @Ignore("slow")
    def "partThree slow"() {
        when:
        final long result = new Year2025Quest09(input).partThree()

        then:
        result == expectedResult

        where:
        input                                   || expectedResult | comment
        readFile("eck/2025/quest9/input_3.txt") || 45463          | ""
    }

}
