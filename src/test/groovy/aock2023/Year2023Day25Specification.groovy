package aock2023

import spock.lang.Ignore
import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2023Day25Specification extends Specification {

    @Ignore("random")
    def "partOne"() {
        when:
        final long result = new Year2023Day25(input).partOne(3)

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        //readFile(2023, 25, "example_1") | 54             | ""

        readFile(2023, 25) | 582692         | "> 1526"
    }

}
