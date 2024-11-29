import spock.lang.Specification

import static shared.InputsKt.readFile

class Day0xSpecification extends Specification {

    def "partOne"() {
        when:
        final long area = 0

        then:
        area == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2015, 4) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long length = 0

        then:
        length == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2015, 4) | 0              | ""
    }

}
