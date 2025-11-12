package shared


import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class CollectionsSpecification extends Specification {

    def "union of lists"() {
        when:
        final List<Integer> result = CollectionsKt.union(values)

        then:
        result == expectedResult

        where:
        values           | expectedResult | comment
        [[1]]            | [1]            | ""
        [[1], [2]]       | [1, 2]         | ""
        [[1, 2], [3]]    | [1, 2, 3]      | ""
        [[1, 2], [3, 4]] | [1, 2, 3, 4]   | ""
    }

    def "transpose list of lists"() {
        when:
        final List<List<Integer>> result = CollectionsKt.transpose(values)

        then:
        assertThat(result).containsExactlyElementsOf(expectedResult)

        where:
        values           | expectedResult   | comment
        [[1], [2], [3]]  | [[1, 2, 3]]      | ""
        [[1, 2], [3, 4]] | [[1, 3], [2, 4]] | ""
    }

    def "take while increasing"() {
        when:
        final result = CollectionsKt.takeWhileIncreasing(values)

        then:
        assertThat(result).containsExactlyElementsOf(expectedResult)

        where:
        values       | expectedResult | comment
        [1, 2, 3]    | [1, 2, 3]      | ""
        [1, 3, 2]    | [1, 3]         | ""
        [1, 1, 3, 2] | [1]            | ""
        [4, 1, 3, 2] | [4]            | ""
    }

    def "take only increasing"() {
        when:
        final result = CollectionsKt.takeOnlyIncreasing(values)

        then:
        assertThat(result).containsExactlyElementsOf(expectedResult)

        where:
        values       | expectedResult | comment
        [1, 2, 3]    | [1, 2, 3]      | ""
        [1, 3, 2]    | [1, 3]         | ""
        [1, 1, 3, 2] | [1, 3]         | ""
        [4, 1, 3, 2] | [4]            | ""
    }

    def "take only visible from height"() {
        when:
        final result = CollectionsKt.takeVisibleFromHeight(values, height)

        then:
        assertThat(result).containsExactlyElementsOf(expectedResult)

        where:
        height | values          | expectedResult  | comment
        5      | [1, 2, 3]       | [1, 2, 3]       | ""
        5      | [1, 3, 2]       | [1, 3, 2]       | ""
        5      | [1, 1, 3, 2]    | [1, 1, 3, 2]    | ""
        5      | [4, 1, 3, 2]    | [4, 1, 3, 2]    | ""

        5      | [1, 2, 5, 3]    | [1, 2, 5]       | ""
        5      | [1, 2, 6, 3]    | [1, 2, 6]       | ""
        5      | [5, 1, 3, 2]    | [5]             | ""
        5      | [6, 1, 3, 2]    | [6]             | ""
        5      | [1, 1, 3, 9, 2] | [1, 1, 3, 9]    | ""
        5      | [4, 1, 3, 2, 5] | [4, 1, 3, 2, 5] | ""
    }

    def "prefixes"() {
        when:
        final Set<String> result = CollectionsKt.prefixes(input)

        then:
        assertThat(result).containsExactlyElementsOf(expected)

        where:
        input                              || expected              | comment
        ["a", "ab", "abc", "b", "bc", "c"] || ["a", "b", "c"]       | ""
        ["abc", "ab", "a"]                 || ["a"]                 | ""
        ["foo", "foobar", "bar", "baz"]    || ["foo", "bar", "baz"] | ""
        ["foo", "fo", "f"]                 || ["f"]                 | ""
        ["apple", "app", "apricot", "a"]   || ["a"]                 | ""
        ["dog", "cat", "do", "ca"]         || ["do", "ca"]          | ""
        ["one", "on", "o", "two", "t"]     || ["o", "t"]            | ""
        []                                 || []                    | ""
    }

}
