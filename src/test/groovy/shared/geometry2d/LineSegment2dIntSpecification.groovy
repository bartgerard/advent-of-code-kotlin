package shared.geometry2d

import spock.lang.Specification

class LineSegment2dIntSpecification extends Specification {

    def "slope and y-intercept"() {
        given:
        final LineSegment2dInt line = new LineSegment2dInt(new Point2dInt(p1), new Point2dInt(p2))

        expect:
        line.slope() == expectedSlope
        line.yIntercept() == expectedIntercept

        where:
        p1      | p2      || expectedSlope | expectedIntercept | comment
        [0, 0]  | [2, 1]  || 0.5d          | 0.0d              | "non-integer slope"
        [1, 1]  | [3, 2]  || 0.5d          | 0.5d              | "non-integer intercept"
        [0, 3]  | [4, -1] || -1.0d         | 3.0d              | ""
        [-2, 5] | [3, 5]  || 0.0d          | 5.0d              | "horizontal"
    }

    def "slope of vertical segment is infinite"() {
        expect:
        new LineSegment2dInt(new Point2dInt(0, 0), new Point2dInt(0, 5)).slope() == Double.POSITIVE_INFINITY
        new LineSegment2dInt(new Point2dInt(0, 0), new Point2dInt(0, -5)).slope() == Double.NEGATIVE_INFINITY
    }

    def "linear equation passes through both end points"() {
        given:
        final LineSegment2dInt line = new LineSegment2dInt(new Point2dInt(p1), new Point2dInt(p2))

        when:
        final equation = line.toLinearEquation()

        then:
        equation.a * p1[0] + equation.b * p1[1] + equation.c == 0
        equation.a * p2[0] + equation.b * p2[1] + equation.c == 0
        equation.at(p1[0]) == p1[1]
        equation.at(p2[0]) == p2[1]
        equation.toLinearFunction() == line.toFunction()

        where:
        p1      | p2      | comment
        [1, 1]  | [3, 2]  | ""
        [0, 3]  | [4, -1] | ""
        [-2, 5] | [3, 5]  | "horizontal"
    }

    def "intersect discrete"() {
        given:
        final LineSegment2dInt l1 = new LineSegment2dInt(new Point2dInt(l1p1), new Point2dInt(l1p2))
        final LineSegment2dInt l2 = new LineSegment2dInt(new Point2dInt(l2p1), new Point2dInt(l2p2))
        final Set<Point2dInt> expectedPoints = expectedResult.collect { new Point2dInt(it) } as Set

        when:
        final List<Point2dInt> result = l1.intersectDiscrete(l2)
        final List<Point2dInt> inverseResult = l2.intersectDiscrete(l1)

        then:
        result as Set == expectedPoints
        inverseResult as Set == expectedPoints
        result.size() == expectedPoints.size()

        where:
        l1p1   | l1p2   | l2p1   | l2p2   || expectedResult           | comment
        [0, 0] | [4, 4] | [0, 4] | [4, 0] || [[2, 2]]                 | "crossing"
        [0, 0] | [1, 1] | [0, 1] | [1, 0] || []                       | "crossing between integer points"
        [0, 0] | [2, 2] | [3, 0] | [3, 5] || []                       | "lines cross outside of the segment"
        [0, 0] | [2, 0] | [2, 0] | [2, 3] || [[2, 0]]                 | "touching end points"
        [0, 0] | [4, 0] | [0, 1] | [4, 1] || []                       | "parallel"

        [0, 9] | [5, 9] | [0, 9] | [2, 9] || [[0, 9], [1, 9], [2, 9]] | "horizontal overlap"
        [5, 9] | [0, 9] | [2, 9] | [0, 9] || [[0, 9], [1, 9], [2, 9]] | "horizontal overlap, reversed"
        [0, 0] | [0, 5] | [0, 3] | [0, 4] || [[0, 3], [0, 4]]         | "vertical overlap"
        [0, 0] | [4, 4] | [2, 2] | [6, 6] || [[2, 2], [3, 3], [4, 4]] | "diagonal overlap"
        [0, 0] | [2, 0] | [3, 0] | [5, 0] || []                       | "collinear, disjoint"

        [2, 2] | [2, 2] | [0, 0] | [4, 4] || [[2, 2]]                 | "single point on segment"
        [1, 2] | [1, 2] | [0, 0] | [4, 4] || []                       | "single point next to segment"
    }

}
