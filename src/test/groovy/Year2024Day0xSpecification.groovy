import aock2024.Year2024Day0x
import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("template")
class Year2024Day0xSpecification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day0x(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2024, 0) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2024Day0x(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2024, 0) | 0              | ""
    }

}
