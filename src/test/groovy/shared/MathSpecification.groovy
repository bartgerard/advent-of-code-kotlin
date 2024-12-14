package shared

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static shared.MathKt.*

class MathSpecification extends Specification {

    def "calculate combinations"() {
        when:
        final long result = combinations(k, n)

        then:
        result == expectedResult

        where:
        k | n | expectedResult | comment
        0 | 1 | 1              | ""
        1 | 1 | 1              | ""

        0 | 2 | 1              | ""
        1 | 2 | 2              | ""
        2 | 2 | 1              | ""

        0 | 3 | 1              | ""
        1 | 3 | 3              | ""
        2 | 3 | 3              | ""
        3 | 3 | 1              | ""

        0 | 4 | 1              | ""
        1 | 4 | 4              | ""
        2 | 4 | 6              | ""
        3 | 4 | 4              | ""
        4 | 4 | 1              | ""

        0 | 5 | 1              | ""
        1 | 5 | 5              | ""
        2 | 5 | 10             | ""
        3 | 5 | 10             | ""
        4 | 5 | 5              | ""
        5 | 5 | 1              | ""

        0 | 6 | 1              | ""
        1 | 6 | 6              | ""
        2 | 6 | 15             | ""
        3 | 6 | 20             | ""
        4 | 6 | 15             | ""
        5 | 6 | 6              | ""
        6 | 6 | 1              | ""

        0 | 7 | 1              | ""
        1 | 7 | 7              | ""
        2 | 7 | 21             | ""
        3 | 7 | 35             | ""
        4 | 7 | 35             | ""
        5 | 7 | 21             | ""
        6 | 7 | 7              | ""
        7 | 7 | 1              | ""
    }

    def "calculate simplex"() {
        when:
        final long result = simplex(k, n)

        then:
        result == expectedResult

        where:
        k | n | expectedResult | comment
        0 | 1 | 1              | ""
        0 | 1 | 1              | ""
        0 | 2 | 1              | ""
        0 | 3 | 1              | ""
        0 | 4 | 1              | ""
        0 | 5 | 1              | ""

        1 | 0 | 1              | ""
        1 | 1 | 2              | ""
        1 | 2 | 3              | ""
        1 | 3 | 4              | ""
        1 | 4 | 5              | ""
        1 | 5 | 6              | ""

        2 | 0 | 1              | ""
        2 | 1 | 3              | ""
        2 | 2 | 6              | ""
        2 | 3 | 10             | ""
        2 | 4 | 15             | ""
        2 | 5 | 21             | ""
        2 | 6 | 28             | ""

        3 | 0 | 1              | ""
        3 | 1 | 4              | ""
        3 | 2 | 10             | ""
        3 | 3 | 20             | ""
        3 | 4 | 35             | ""
        3 | 5 | 56             | ""
        3 | 6 | 84             | ""

        4 | 0 | 1              | ""
        4 | 1 | 5              | ""
        4 | 2 | 15             | ""
        4 | 3 | 35             | ""
        4 | 4 | 70             | ""
        4 | 5 | 126            | ""
        4 | 6 | 210            | ""

        5 | 0 | 1              | ""
        5 | 1 | 6              | ""
        5 | 2 | 21             | ""
        5 | 3 | 56             | ""
        5 | 4 | 126            | ""
        5 | 5 | 252            | ""
        5 | 6 | 462            | ""
    }

    def "calculate triangular number for n"() {
        when:
        final long result = triangular(value)

        then:
        result == expectedResult

        where:
        value | expectedResult | comment
        1     | 1              | ""
        2     | 3              | ""
        3     | 6              | ""
        4     | 10             | ""
        5     | 15             | ""
        6     | 21             | ""
    }

    def "calculate prime factors"() {
        when:
        final Map<Long, Integer> result = primeFactors(values)

        then:
        assertThat(result).containsExactlyEntriesOf(expectedResult)

        where:
        values | expectedResult | comment
        1      | [:]            | ""
        2      | [2L: 1]        | ""
        3      | [3L: 1]        | ""
        4      | [2L: 2]        | ""
        6      | [2L: 1, 3L: 1] | ""
        12     | [2L: 2, 3L: 1] | ""
        15     | [3L: 1, 5L: 1] | ""
    }

    def "calculate least common multiple"() {
        when:
        final long result = lcm(values)

        then:
        result == expectedResult

        where:
        values     | expectedResult | comment
        [1]        | 1              | ""
        [1, 2]     | 2              | ""
        [2, 3]     | 6              | ""
        [3, 4]     | 12             | ""
        [2, 3, 4]  | 12             | ""
        [2, 5, 13] | 130            | ""
    }

    def "calculate greatest common divider"() {
        when:
        final long result = gcd(value1, value2)

        then:
        result == expectedResult

        where:
        value1 | value2 | expectedResult | comment
        6      | 4      | 2              | ""
        4      | 6      | 2              | ""
        1      | 13     | 1              | ""
        1024   | 56     | 8              | ""
        1024   | 64     | 64             | ""
    }

}
