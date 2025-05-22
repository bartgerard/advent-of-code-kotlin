package aock2019


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day09Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day09(program).partOne(input).diagnosticCode()

        then:
        result == expectedResult

        where:
        program                                                     | input | expectedResult    | comment
        "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99" | []    | 99                | "takes no input and produces a copy of itself as output"
        "1102,34915192,34915192,7,4,7,99,0"                         | [1L]  | 1219070632396864L | "should output a 16-digit number"
        "104,1125899906842624,99"                                   | [1L]  | 1125899906842624L | "should output the large number in the middle"

        readFile(2019, 9)                                           | [1L]  | 2682107844L       | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2019Day09(program).partTwo(input).diagnosticCode()

        then:
        result == expectedResult

        where:
        program           | input | expectedResult | comment
        readFile(2019, 9) | [2L]  | 34738          | ""
    }

}
