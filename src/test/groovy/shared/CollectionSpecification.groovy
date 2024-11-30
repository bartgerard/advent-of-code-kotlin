package shared

import spock.lang.Specification


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

}
