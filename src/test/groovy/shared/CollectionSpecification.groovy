package shared


import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class CollectionSpecification extends Specification {

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

}
