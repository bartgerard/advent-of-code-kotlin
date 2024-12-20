package aock2019


import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static shared.InputsKt.readFile

class Year2019Day02Specification extends Specification {

    def "partOne examples"() {
        when:
        final List<Long> result = new Year2019Day02(input).runProgram()

        then:
        assertThat(result).containsExactlyElementsOf(expectedResult)

        where:
        input                 | expectedResult                | comment
        "1,0,0,0,99"          | [2, 0, 0, 0, 99]              | "[2, 0, 0, 0, 99]"
        "2,3,0,3,99"          | [2, 3, 0, 6, 99]              | "[2, 3, 0, 6, 99] "
        "2,4,4,5,99,0"        | [2, 4, 4, 5, 99, 9801]        | "[2, 4, 4, 5, 99, 9801]"
        "1,1,1,4,99,5,6,0,99" | [30, 1, 1, 4, 2, 5, 6, 0, 99] | "[30, 1, 1, 4, 2, 5, 6, 0, 99]"
    }

    def "partOne"() {
        when:
        final List<Long> result = new Year2019Day02(input).partOne(12, 2)

        then:
        result[0] == expectedResult

        where:
        input             | expectedResult | comment
        readFile(2019, 2) | 6627023        | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day02(input).partTwo(address0)

        then:
        result == expectedResult

        where:
        input             | address0 | expectedResult | comment
        readFile(2019, 2) | 19690720 | 4019           | ""
    }

}
