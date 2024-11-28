package aock2015

import spock.lang.Specification

import static shared.InputsKt.readFile

class Day3Specification extends Specification {

    def "how many houses receive at least one present"() {
        when:
        final long area = new Day3(input).atLeastOnePresent().size()

        then:
        area == expectedResult

        where:
        input             | expectedResult | comment
        ">"               | 2              | ""
        "^>v<"            | 4              | ""
        "^v^v^v^v^v"      | 2              | ""

        readFile(2015, 3) | 2081           | ""
    }

    def "how many houses receive at least one present with Robo-Santa"() {
        when:
        final long length = new Day3(input).atLeastOnePresentWithHelp(2).size()

        then:
        length == expectedResult

        where:
        input             | expectedResult | comment
        "^v"              | 3              | ""
        "^>v<"            | 3              | ""
        "^v^v^v^v^v"      | 11             | ""

        readFile(2015, 3) | 2341           | ""
    }

}
