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
        a  | b | c  | x  || expectedResult | comment
        -2 | 1 | -3 | 5  || 13             | ""
        4  | 1 | -7 | -2 || 15             | ""
        -1 | 2 | 2  | 4  || 1              | ""
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
        a  | b | c  || expectedSlope | expectedIntercept | comment
        -2 | 1 | -3 || 2             | 3                 | ""
        4  | 1 | -7 || -4            | 7                 | ""
        -1 | 2 | 2  || 0.5           | -1                | ""
    }

    def "intersect two linear equations"() {
        when:
        final intersection = eq1.intersect(eq2)

        then:
        intersection.first == expectedResult.first
        intersection.second == expectedResult.second

        where:
        eq1                                 | eq2                                 || expectedResult            | comment
        new LinearEquation2d(94, 22, -8400) | new LinearEquation2d(34, 67, -5400) || new Pair(80.0, 40.0)      | ""
        new LinearEquation2d(17, 84, -7870) | new LinearEquation2d(86, 37, -6450) || new Pair(38.0, 86.0)      | ""
        new LinearEquation2d(-2, 1, -3)     | new LinearEquation2d(4, 1, -7)      || new Pair(2d / 3, 13d / 3) | "same convention as at()"
    }

    def "intersection lies on both linear equations"() {
        given:
        final eq1 = new LinearEquation2d(a1, b1, c1)
        final eq2 = new LinearEquation2d(a2, b2, c2)

        when:
        final intersection = eq1.intersect(eq2)

        then:
        Math.abs(eq1.at(intersection.first) - intersection.second) < 1e-9
        Math.abs(eq2.at(intersection.first) - intersection.second) < 1e-9

        where:
        a1 | b1 | c1 | a2 | b2 | c2  | comment
        -2 | 1  | -3 | 4  | 1  | -7  | ""
        -1 | 2  | 2  | 3  | 5  | 11  | ""
        7  | -3 | 0  | 1  | 1  | -20 | ""
    }

    def "intersect parallel linear equations"() {
        expect:
        new LinearEquation2d(1, 1, 0).intersect(new LinearEquation2d(2, 2, 5)) == null
    }

    def "convert linear function to general form and back"() {
        given:
        final function = new LinearFunction2d(m as double, y0 as double)

        when:
        final equation = function.toGeneralForm()

        then:
        equation.at(x) == function.at(x)
        equation.slope == m
        equation.intercept == y0
        equation.toLinearFunction() == function

        where:
        m   | y0 | x  | comment
        2   | 3  | 5  | ""
        -4  | 7  | -2 | ""
        0.5 | -1 | 4  | ""
    }

    def "calculate y for linear function"() {
        given:
        final function = new LinearFunction2d(m as double, y0 as double)

        when:
        final double result = function.at(x)

        then:
        result == expectedResult

        where:
        m   | y0 | x  || expectedResult | comment
        2   | 3  | 5  || 13             | ""
        -4  | 7  | -2 || 15             | ""
        0.5 | -1 | 4  || 1              | ""
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
        m   | y0 || expectedSlope | expectedIntercept | comment
        2   | 3  || 2             | 3                 | ""
        -4  | 7  || -4            | 7                 | ""
        0.5 | -1 || 0.5           | -1                | ""
    }


}
