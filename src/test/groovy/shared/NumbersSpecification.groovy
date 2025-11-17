package shared


import spock.lang.Specification

class NumbersSpecification extends Specification {

    def "factorize"() {
        when:
        final factorization = NumbersKt.factorize(n)

        then:
        factorization == expectedResult

        where:
        n   || expectedResult | comment
        1   || [1]            | ""
        2   || [2]            | ""
        3   || [3]            | ""
        4   || [2, 2]         | ""
        5   || [5]            | ""
        6   || [2, 3]         | ""
        9   || [3, 3]         | ""
        12  || [2, 2, 3]      | ""

        0   || []             | ""
        -1  || [-1]           | ""
        -12 || [-1, 2, 2, 3]  | ""
    }

    def "complex numbers - addition"() {
        when:
        final result = ComplexNumber.parse(c1) + ComplexNumber.parse(c2)
        final expectedResult = ComplexNumber.parse(expected)

        then:
        result == expectedResult

        where:
        c1        | c2        || expected  | comment
        "[1,1]"   | "[2,2]"   || "[3,3]"   | ""
        "[2,5]"   | "[3,7]"   || "[5,12]"  | ""
        "[-2,5]"  | "[10,-1]" || "[8,4]"   | ""
        "[-1,-2]" | "[-3,-4]" || "[-4,-6]" | ""
    }

    def "complex numbers - multiplication"() {
        when:
        final result = ComplexNumber.parse(c1).times(ComplexNumber.parse(c2))
        final expectedResult = ComplexNumber.parse(expected)

        then:
        result == expectedResult

        where:
        c1        | c2        || expected   | comment
        "[1,1]"   | "[2,2]"   || "[0,4]"    | ""
        "[2,5]"   | "[3,7]"   || "[-29,29]" | ""
        "[-2,5]"  | "[10,-1]" || "[-15,52]" | ""
        "[-1,-2]" | "[-3,-4]" || "[-5,10]"  | ""
    }

    def "complex numbers - division"() {
        when:
        final result = ComplexNumber.parse(c1) / ComplexNumber.parse(c2)
        final expectedResult = ComplexNumber.parse(expected)

        then:
        result == expectedResult

        where:
        c1          | c2      || expected  | comment
        "[10,12]"   | "[2,2]" || "[5,6]"   | ""
        "[11,12]"   | "[3,5]" || "[3,2]"   | ""
        "[-10,-12]" | "[2,2]" || "[-5,-6]" | ""
        "[-11,-12]" | "[3,5]" || "[-3,-2]" | ""
    }

}
