package aock2019

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2019Day08Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2019Day08(input).partOne(width, height)

        then:
        result == expectedResult

        where:
        input             | width | height | expectedResult | comment
        "123456789012"    | 3     | 2      | 1              | ""

        readFile(2019, 8) | 25    | 6      | 2048           | ""
    }

    @Ignore("visual")
    def "partTwo"() {
        when:
        final long result = new Year2019Day08(input).partTwo(width, height)

        then:
        result == expectedResult

        where:
        input              | width | height | expectedResult | comment
        "0222112222120000" | 2     | 2      | 0              | ""

        readFile(2019, 8)  | 25    | 6      | 0              | "HFYAK"
    }

}
