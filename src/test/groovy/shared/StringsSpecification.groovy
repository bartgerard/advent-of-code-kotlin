package shared


import spock.lang.Specification

class StringsSpecification extends Specification {

    def "split in n"() {
        when:
        final substrings = StringsKt.splitIn(s, n)

        then:
        substrings == expectedResult

        where:
        s     | n | expectedResult  | comment
        "a"   | 1 | ["a"]           | ""
        "aa"  | 1 | ["aa"]          | ""
        "aa"  | 2 | ["a", "a"]      | ""
        "a"   | 3 | ["a"]           | ""
        "aa"  | 3 | ["aa"]          | ""
        "aaa" | 3 | ["a", "a", "a"] | ""
    }

}
