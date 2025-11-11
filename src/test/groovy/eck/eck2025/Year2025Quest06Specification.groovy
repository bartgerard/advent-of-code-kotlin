package eck.eck2025

import ec2025.Year2025Quest06
import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2025Quest06Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2025Quest06(input).partOne()

        then:
        result == expectedResult

        where:
        input                                   | expectedResult | comment
        "ABabACacBCbca"                         | 5              | ""

        readFile("eck/2025/quest6/input_1.txt") | 166            | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2025Quest06(input).partTwo()

        then:
        result == expectedResult

        where:
        input                                   | expectedResult | comment
        "ABabACacBCbca"                         | 11             | ""

        readFile("eck/2025/quest6/input_2.txt") | 3452           | ""
    }

    @Ignore("slow")
    def "partThree"() {
        when:
        final long result = new Year2025Quest06(input).partThree(repetitions, distanceLimit)

        then:
        result == expectedResult

        where:
        input                                   | repetitions | distanceLimit | expectedResult | comment
        "AABCBABCABCabcabcABCCBAACBCa"          | 1           | 10            | 34             | ""
        "AABCBABCABCabcabcABCCBAACBCa"          | 2           | 10            | 72             | ""
        "AABCBABCABCabcabcABCCBAACBCa"          | 1000        | 1000          | 3442321        | ""

        readFile("eck/2025/quest6/input_3.txt") | 1000        | 1000          | 1665848677     | ""
    }

}
