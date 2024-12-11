package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day11Specification extends Specification {

    def "how many stones will you have after blinking 25 times"() {
        when:
        final long result = new Year2024Day11(input).stonesAfter(blinks)

        then:
        result == expectedResult

        where:
        input              | blinks | expectedResult | comment
        "0 1 10 99 999"    | 1      | 7              | ""
        "125 17"           | 6      | 22             | ""
        "125 17"           | 25     | 55312          | ""

        readFile(2024, 11) | 25     | 218956         | ""
    }

    def "how many stones would you have after blinking a total of 75 times"() {
        when:
        final long result = new Year2024Day11(input).stonesAfter(blinks)

        then:
        result == expectedResult

        where:
        input              | blinks | expectedResult  | comment
        readFile(2024, 11) | 75     | 259593838049805 | ""
    }

}
