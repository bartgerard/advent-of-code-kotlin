package aock2015

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("slow")
class Year2015Day04Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2015Day04(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "abcdef"          | 609043         | ""
        "pqrstuv"         | 1048970        | ""

        readFile(2015, 4) | 117946         | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2015Day04(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        readFile(2015, 4) | 3938038        | ""
    }

}
