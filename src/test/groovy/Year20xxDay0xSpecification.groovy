import spock.lang.Specification

import static shared.InputsKt.readFile

class Year20xxDay0xSpecification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year20xxDayXx(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2015, 4) | 0              | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year20xxDayXx(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        ""                | 0              | ""

        readFile(2015, 4) | 0              | ""
    }

}
