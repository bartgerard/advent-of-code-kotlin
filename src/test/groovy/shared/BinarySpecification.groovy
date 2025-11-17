package shared

import spock.lang.Specification

class BinarySpecification extends Specification {

    def "inverse"() {
        given:
        final binaryNumber = BinaryNumber.parse(number)
        final expectedBinaryNumber = BinaryNumber.parse(expectedResult)

        when:
        final BinaryNumber result = binaryNumber.inverse()

        then:
        result == expectedBinaryNumber

        where:
        number         || expectedResult | comment
        "011001"       || "100110"       | ""
        "011111100101" || "100000011010" | ""
    }

    def "intValue"() {
        given:
        final binaryNumber = BinaryNumber.parse(number)

        when:
        final result = binaryNumber.intValue

        then:
        result == expectedResult

        where:
        number         || expectedResult | comment
        "011001"       || 25             | ""
        "011111100101" || 2021           | ""
    }

}
