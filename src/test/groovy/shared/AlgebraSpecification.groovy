package shared

import kotlin.Pair
import spock.lang.Specification

class AlgebraSpecification extends Specification {

    def "calculate y for linear equation"() {
        given:
        final equation = new LinearEquation2d(a, b, c)

        when:
        final double result = equation.at(x)

        then:
        result == expectedResult

        where:
        a  | b | c  | x  | expectedResult | comment
        -2 | 1 | -3 | 5  | 13             | ""
        4  | 1 | -7 | -2 | 15             | ""
        -1 | 2 | 2  | 4  | 1              | ""
    }

    def "calculate slope and y-intercept for linear equation"() {
        given:
        final equation = new LinearEquation2d(a, b, c)

        when:
        final double slope = equation.slope
        final double yIntercept = equation.intercept

        then:
        slope == expectedSlope
        yIntercept == expectedIntercept

        where:
        a  | b | c  | expectedSlope | expectedIntercept | comment
        -2 | 1 | -3 | 2             | 3                 | ""
        4  | 1 | -7 | -4            | 7                 | ""
        -1 | 2 | 2  | 0.5           | -1                | ""
    }

    def "intersect two linear equations"() {
        when:
        final intersection = eq1.intersect(eq2)

        then:
        intersection.first == expectedResult.first
        intersection.second == expectedResult.second

        where:
        eq1                                | eq2                                | expectedResult       | comment
        new LinearEquation2d(94, 22, 8400) | new LinearEquation2d(34, 67, 5400) | new Pair(80.0, 40.0) | ""
        new LinearEquation2d(17, 84, 7870) | new LinearEquation2d(86, 37, 6450) | new Pair(38.0, 86.0) | ""
    }

    def "calculate y for linear function"() {
        given:
        final function = new LinearFunction2d(m as double, y0 as double)

        when:
        final double result = function.at(x)

        then:
        result == expectedResult

        where:
        m   | y0 | x  | expectedResult | comment
        2   | 3  | 5  | 13             | ""
        -4  | 7  | -2 | 15             | ""
        0.5 | -1 | 4  | 1              | ""
    }

    def "calculate slope and y-intercept for linear function"() {
        given:
        final function = new LinearFunction2d(m as double, y0 as double)

        when:
        final double slope = function.slope
        final double yIntercept = function.intercept

        then:
        slope == expectedSlope
        yIntercept == expectedIntercept

        where:
        m   | y0 | expectedSlope | expectedIntercept | comment
        2   | 3  | 2             | 3                 | ""
        -4  | 7  | -4            | 7                 | ""
        0.5 | -1 | 0.5           | -1                | ""
    }


}
