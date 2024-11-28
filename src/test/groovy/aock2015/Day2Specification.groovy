package aock2015

import spock.lang.Specification

import static shared.InputsKt.readFileForDay

class Day2Specification extends Specification {

    def "how many total square feet of wrapping paper should they order"() {
        when:
        final long area = new Day2(input).requiredAreaOfWrappingPaper()

        then:
        area == expectedResult

        where:
        input             | expectedResult | comment
        "2x3x4"           | 58             | "2*6 + 2*12 + 2*8 = 52 then + 6 square feet of slack"
        "1x1x10"          | 43             | "2*1 + 2*10 + 2*10 = 42 then + 1 square foot of slack"

        readFileForDay(2) | 1588178        | ""
    }

    def "how many total feet of ribbon should they order"() {
        when:
        final long length = new Day2(input).requiredLengthOfRibbon()

        then:
        length == expectedResult

        where:
        input             | expectedResult | comment
        "2x3x4"           | 34             | ""
        "1x1x10"          | 14             | ""

        readFileForDay(2) | 3783758        | ""
    }

}
