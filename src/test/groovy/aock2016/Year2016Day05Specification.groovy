package aock2016

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

@Ignore("slow")
class Year2016Day05Specification extends Specification {

    def "partOne"() {
        when:
        final String result = new Year2016Day05(input).partOne()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "abc"             | "18f47a30"     | ""

        readFile(2016, 5) | "801b56a7"     | ""
    }

    def "partTwo"() {
        when:
        final String result = new Year2016Day05(input).partTwo()

        then:
        result == expectedResult

        where:
        input             | expectedResult | comment
        "abc"             | "05ace8e3"     | ""

        readFile(2016, 5) | "424a0197"     | ""
    }

}
