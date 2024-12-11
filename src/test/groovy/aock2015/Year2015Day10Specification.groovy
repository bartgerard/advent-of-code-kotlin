package aock2015

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day10Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2015Day10(input).lookAndSay(repetitions)

        then:
        result == expectedResult

        where:
        input              | repetitions | expectedResult | comment
        "211"              | 1           | 4              | ""

        "1"                | 1           | 2              | ""
        "11"               | 1           | 2              | ""
        "21"               | 1           | 4              | ""
        "1211"             | 1           | 6              | ""
        "111221"           | 1           | 6              | ""

        "1"                | 5           | 6              | ""

        readFile(2015, 10) | 40          | 492982         | ""
    }

    @Ignore("slow")
    def "partTwo"() {
        when:
        final long result = new Year2015Day10(input).lookAndSay(repetitions)

        then:
        result == expectedResult

        where:
        input              | repetitions | expectedResult | comment
        readFile(2015, 10) | 50          | 6989950        | ""
    }

}
