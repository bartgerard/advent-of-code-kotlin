package shared

import spock.lang.Specification

import static java.util.Objects.isNull

class Geometry2dSpecification extends Specification {

    def "intersect lines"() {
        given:
        final LineSegment2d l1 = new LineSegment2d(new Point2d(l1p1), new Point2d(l1p2))
        final LineSegment2d l2 = new LineSegment2d(new Point2d(l2p1), new Point2d(l2p2))

        when:
        final Point2d result = l1.intersect(l2)

        then:
        final Point2d expectedPoint = isNull(expectedResult) ? null : new Point2d(expectedResult)
        result == expectedPoint

        where:
        l1p1    | l1p2   | l2p1   | l2p2    | expectedResult | comment
        [0, 0]  | [1, 0] | [0, 1] | [1, 1]  | null           | "parallel"
        [0, 0]  | [1, 0] | [0, 0] | [1, 0]  | null           | "coinciding"

        [0, 0]  | [1, 0] | [0, 1] | [1, 0]  | [1, 0]         | ""
        [0, 0]  | [2, 2] | [0, 2] | [2, 0]  | [1, 1]         | ""
        [0, 0]  | [1, 1] | [0, 1] | [1, -1] | [0, 0]         | ""
        [-1, 0] | [1, 0] | [0, 1] | [0, -1] | [0, 0]         | ""

        [1, 0]  | [1, 1] | [0, 1] | [1, 1]  | [1, 1]         | ""
        [0, 0]  | [1, 1] | [0, 1] | [1, 1]  | [1, 1]         | ""

        [2, 0]  | [2, 1] | [0, 2] | [1, 2]  | [2, 2]         | ""
        [0, 0]  | [1, 1] | [0, 2] | [1, 2]  | [2, 2]         | ""
    }

    def "intersect lines within segment"() {
        given:
        final LineSegment2d l1 = new LineSegment2d(new Point2d(l1p1), new Point2d(l1p2))
        final LineSegment2d l2 = new LineSegment2d(new Point2d(l2p1), new Point2d(l2p2))

        when:
        final Point2d result = l1.intersectWithinSegment(l2)

        then:
        final Point2d expectedPoint = isNull(expectedResult) ? null : new Point2d(expectedResult)
        result == expectedPoint

        where:
        l1p1    | l1p2   | l2p1   | l2p2    | expectedResult | comment
        [0, 0]  | [1, 0] | [0, 1] | [1, 1]  | null           | "parallel"
        [0, 0]  | [1, 0] | [0, 0] | [1, 0]  | null           | "coinciding"

        [0, 0]  | [1, 0] | [0, 1] | [1, 0]  | [1, 0]         | ""
        [0, 0]  | [2, 2] | [0, 2] | [2, 0]  | [1, 1]         | ""
        [0, 0]  | [1, 1] | [0, 1] | [1, -1] | [0, 0]         | ""
        [-1, 0] | [1, 0] | [0, 1] | [0, -1] | [0, 0]         | ""

        [1, 0]  | [1, 1] | [0, 1] | [1, 1]  | [1, 1]         | ""
        [0, 0]  | [1, 1] | [0, 1] | [1, 1]  | [1, 1]         | ""

        [2, 0]  | [2, 1] | [0, 2] | [1, 2]  | null           | ""
        [0, 0]  | [1, 1] | [0, 2] | [1, 2]  | null           | ""
    }

}
