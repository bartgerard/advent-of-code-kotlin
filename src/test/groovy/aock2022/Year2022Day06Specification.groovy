package aock2022


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2022Day06Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2022Day06(input).partOne()

        then:
        result == expectedResult

        where:
        input                               | expectedResult | comment
        "bvwbjplbgvbhsrlpgdmjqwftvncz"      | 5              | ""
        "nppdvjthqldpwncqszvftbrmjlhg"      | 6              | ""
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" | 10             | ""
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"  | 11             | ""

        readFile(2022, 6)                   | 1623           | ""
    }

    def "partTwo"() {
        when:
        final long result = new Year2022Day06(input).partTwo()

        then:
        result == expectedResult

        where:
        input                               | expectedResult | comment
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb"    | 19             | ""
        "bvwbjplbgvbhsrlpgdmjqwftvncz"      | 23             | ""
        "nppdvjthqldpwncqszvftbrmjlhg"      | 23             | ""
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" | 29             | ""
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"  | 26             | ""

        readFile(2022, 6)                   | 3774           | ""
    }

}
