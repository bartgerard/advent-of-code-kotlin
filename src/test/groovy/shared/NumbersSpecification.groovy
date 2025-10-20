package shared


import spock.lang.Specification

class NumbersSpecification extends Specification {

    def "factorize"() {
        when:
        final factorization = NumbersKt.factorize(n)

        then:
        factorization == expectedResult

        where:
        n   | expectedResult | comment
        1   | [1]            | ""
        2   | [2]            | ""
        3   | [3]            | ""
        4   | [2, 2]         | ""
        5   | [5]            | ""
        6   | [2, 3]         | ""
        9   | [3, 3]         | ""
        12  | [2, 2, 3]      | ""

        0   | []             | ""
        -1  | [-1]           | ""
        -12 | [-1, 2, 2, 3]  | ""
    }

}
