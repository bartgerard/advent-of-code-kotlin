package aock2015

import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day02Specification extends Specification {

    def "how many total square feet of wrapping paper should they order"() {
        when:
        final long area = new Year2015Day02(input).requiredAreaOfWrappingPaper()

        then:
        area == expectedResult

        where:
        input             | expectedResult | comment
        "2x3x4"           | 58             | "2*6 + 2*12 + 2*8 = 52 then + 6 square feet of slack"
        "1x1x10"          | 43             | "2*1 + 2*10 + 2*10 = 42 then + 1 square foot of slack"

        readFile(2015, 2) | 1588178        | ""
    }

    def "how many total feet of ribbon should they order"() {
        when:
        final long length = new Year2015Day02(input).requiredLengthOfRibbon()

        then:
        length == expectedResult

        where:
        input             | expectedResult | comment
        "2x3x4"           | 34             | ""
        "1x1x10"          | 14             | ""

        readFile(2015, 2) | 3783758        | ""
    }

}
