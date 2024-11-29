package aock2015

import spock.lang.Specification

import static shared.InputsKt.downloadInputFile
import static shared.InputsKt.readFile

class Day04Specification extends Specification {

    def "partOne"() {
        when:

        final long area = new Day03(input).atLeastOnePresent().size()

        then:
        area == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2015, 4) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long length = new Day03(input).atLeastOnePresentWithHelp(2).size()

        then:
        length == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2015, 4) | 0              | ""
    }

}
