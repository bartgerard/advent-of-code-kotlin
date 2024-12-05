package aock2015


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2015Day05Specification extends Specification {

    def "how many strings are nice"() {
        when:
        final long result = new Year2015Day05(input).partOne()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        "ugknbfddgicrmopn" | 1              | "nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings"
        "aaa"              | 1              | "nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap"
        "jchzalrnumimnmhp" | 0              | "naughty because it has no double letter"
        "haegwjzuvuyypxyu" | 0              | "naughty because it contains the string xy"
        "dvszwmarrgswjxmb" | 0              | "naughty because it contains only one vowel"

        readFile(2015, 5)  | 255            | "248<x<556"
    }

    def "how many strings are nice under these new rules"() {
        when:
        final long result = new Year2015Day05(input).partTwo()

        then:
        result == expectedResult

        where:
        input              | expectedResult | comment
        "qjhvhtzxzqqjkmpb" | 1              | "nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz)"
        "xxyxx"            | 1              | "nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap"
        "uurcxstgmygtbstg" | 0              | "naughty because it has a pair (tg) but no repeat with a single letter between them"
        "ieodomkazucvgmuy" | 0              | "naughty because it has a repeating letter with one between (odo), but no pair that appears twice"

        readFile(2015, 5)  | 55             | ""
    }

}
